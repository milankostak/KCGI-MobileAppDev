package edu.kcg.mobile.notifications.handlers

import android.app.IntentService
import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.*
import androidx.core.content.ContextCompat
import edu.kcg.mobile.notifications.GlobalNotificationBuilder.notificationCompatBuilderInstance
import edu.kcg.mobile.notifications.MainActivity
import edu.kcg.mobile.notifications.R
import edu.kcg.mobile.notifications.mock.MockDatabase.getMessagingStyleData
import edu.kcg.mobile.notifications.util.NotificationUtil.createNotificationChannel

/**
 * Asynchronously handles updating messaging app posts (and active Notification) with replies from
 * user in a conversation. Notification for social app use MessagingStyle.
 */
class MessagingIntentService : IntentService("MessagingIntentService") {

    override fun onHandleIntent(intent: Intent?) {
        if (intent != null) {
            val action = intent.action
            if (ACTION_REPLY == action) {
                handleActionReply(getMessage(intent))
            }
        }
    }

    /**
     * Handles action for replying to messages from the notification.
     */
    private fun handleActionReply(replyCharSequence: CharSequence?) {
        if (replyCharSequence != null) {
            /*
             * You have two options for updating your notification (this class uses approach #2):
             *
             *  1. Use a new NotificationCompatBuilder to create the Notification. This approach
             *  requires you to get *ALL* the information that existed in the previous
             *  Notification (and updates) and pass it to the builder. This is the approach used in
             *  the MainActivity.
             *
             *  2. Use the original NotificationCompatBuilder to create the Notification. This
             *  approach requires you to store a reference to the original builder. The benefit is
             *  you only need the new/updated information. In our case, the reply from the user
             *  which we already have here.
             *
             *  IMPORTANT NOTE: You shouldn't save/modify the resulting Notification object using
             *  its member variables and/or legacy APIs. If you want to retain anything from update
             *  to update, retain the Builder as option 2 outlines.
             */

            // Retrieves NotificationCompat.Builder used to create initial Notification
            var notificationCompatBuilder = notificationCompatBuilderInstance

            // Recreate builder from persistent state if app process is killed
            if (notificationCompatBuilder == null) {
                // Note: New builder set globally in the method
                notificationCompatBuilder = recreateBuilderWithMessagingStyle()
            }

            // Since we are adding to the MessagingStyle, we need to first retrieve the
            // current MessagingStyle from the Notification itself.
            var notification = notificationCompatBuilder.build()
            val messagingStyle =
                    NotificationCompat.MessagingStyle.extractMessagingStyleFromNotification(notification)

            // Add new message to the MessagingStyle. Set last parameter to null for responses
            // from user.
            messagingStyle?.addMessage(replyCharSequence, System.currentTimeMillis(), null as Person?)

            // Updates the Notification
            notification = notificationCompatBuilder.setStyle(messagingStyle).build()

            // Pushes out the updated Notification
            val notificationManagerCompat = NotificationManagerCompat.from(applicationContext)
            notificationManagerCompat.notify(MainActivity.NOTIFICATION_ID, notification)
        }
    }

    /**
     * Extracts CharSequence created from the RemoteInput associated with the Notification.
     */
    private fun getMessage(intent: Intent): CharSequence? {
        val remoteInput = RemoteInput.getResultsFromIntent(intent)
        return remoteInput?.getCharSequence(EXTRA_REPLY)
    }

    /**
     * This recreates the notification from the persistent state in case the app process was killed.
     * It is basically the same code for creating the Notification from MainActivity.
     */
    private fun recreateBuilderWithMessagingStyle(): NotificationCompat.Builder {

        // Main steps for building a MESSAGING_STYLE notification:
        //      0. Get your data
        //      1. Create/Retrieve Notification Channel for O and beyond devices (26+)
        //      2. Build the MESSAGING_STYLE
        //      3. Set up main Intent for notification
        //      4. Set up RemoteInput (users can input directly from notification)
        //      5. Build and issue the notification

        // 0. Get your data (everything unique per Notification)
        val messagingStyleCommsAppData = getMessagingStyleData(applicationContext)

        // 1. Create/Retrieve Notification Channel for O and beyond devices (26+).
        val notificationChannelId = createNotificationChannel(this, messagingStyleCommsAppData)

        // 2. Build the NotificationCompat.Style (MESSAGING_STYLE).
        val contentTitle = messagingStyleCommsAppData.contentTitle
        val messagingStyle = NotificationCompat.MessagingStyle(messagingStyleCommsAppData.me)
                .setConversationTitle(contentTitle)

        // Adds all Messages.
        // Note: Messages include the text, timestamp, and sender.
        for (message in messagingStyleCommsAppData.messages) {
            messagingStyle.addMessage(message)
        }
        messagingStyle.isGroupConversation = messagingStyleCommsAppData.isGroupConversation

        // 3. Set up main Intent for notification.
        val notifyIntent = Intent(this, MessagingMainActivity::class.java)

        // When creating your Intent, you need to take into account the back state, i.e., what
        // happens after your Activity launches and the user presses the back button.

        // There are two options:
        //      1. Regular activity - You're starting an Activity that's part of the application's
        //      normal workflow.

        //      2. Special activity - The user only sees this Activity if it's started from a
        //      notification. In a sense, the Activity extends the notification by providing
        //      information that would be hard to display in the notification itself.

        // Even though this sample's MainActivity doesn't link to the Activity this Notification
        // launches directly, i.e., it isn't part of the normal workflow, a chat app generally
        // always links to individual conversations as part of the app flow, so we will follow
        // option 1.

        // For more information, check out our dev article:
        // https://developer.android.com/training/notify-user/navigation.html

        val stackBuilder = TaskStackBuilder.create(this)
        // Adds the back stack
        stackBuilder.addParentStack(MessagingMainActivity::class.java)
        // Adds the Intent to the top of the stack
        stackBuilder.addNextIntent(notifyIntent)
        // Gets a PendingIntent containing the entire back stack
        val mainPendingIntent = PendingIntent.getActivity(
            this,
            0,
            notifyIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        // 4. Set up RemoteInput, so users can input (keyboard and voice) from notification.

        // Note: For API <24 (M and below) we need to use an Activity, so the lock-screen present
        // the auth challenge. For API 24+ (N and above), we use a Service (could be a
        // BroadcastReceiver), so the user can input from Notification or lock-screen (they have
        // choice to allow) without leaving the notification.

        // Create the RemoteInput specifying this key.
        val replyLabel = getString(R.string.reply_label)
        val remoteInput = RemoteInput.Builder(EXTRA_REPLY)
                .setLabel(replyLabel)
                .setChoices(messagingStyleCommsAppData.replyChoicesBasedOnLastMessage)
                .build()
        val intent = Intent(this, MessagingIntentService::class.java)
        intent.action = ACTION_REPLY
        val replyActionPendingIntent = PendingIntent.getService(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val replyAction = NotificationCompat.Action.Builder(
                R.drawable.ic_reply_white_18dp,
                replyLabel,
                replyActionPendingIntent)
                .addRemoteInput(remoteInput) // Informs system we aren't bringing up our own custom UI for a reply
                // action.
                .setShowsUserInterface(false) // Allows system to generate replies by context of conversation.
                .setAllowGeneratedReplies(true)
                .setSemanticAction(NotificationCompat.Action.SEMANTIC_ACTION_REPLY)
                .build()

        // 5. Build and issue the notification.

        // Because we want this to be a new notification (not updating current notification), we
        // create a new Builder. Later, we update this same notification, so we need to save this
        // Builder globally (as outlined earlier).
        val notificationCompatBuilder = NotificationCompat.Builder(applicationContext, notificationChannelId!!)
        notificationCompatBuilderInstance = notificationCompatBuilder
        notificationCompatBuilder // MESSAGING_STYLE sets title and content for API 16 and above devices.
                .setStyle(messagingStyle) // Title for API < 16 devices.
                .setContentTitle(contentTitle) // Content for API < 16 devices.
                .setContentText(messagingStyleCommsAppData.contentText)
                .setSmallIcon(R.drawable.ic_launcher)
                .setLargeIcon(
                        BitmapFactory.decodeResource(
                                resources, R.drawable.ic_person_black_48dp))
                .setContentIntent(mainPendingIntent)
                .setDefaults(NotificationCompat.DEFAULT_ALL) // Set primary color (important for Wear 2.0 Notifications).
                .setColor(ContextCompat.getColor(applicationContext, R.color.colorPrimary)) // SIDE NOTE: Auto-bundling is enabled for 4 or more notifications on API 24+ (N+)
                // devices and all Wear devices. If you have more than one notification and
                // you prefer a different summary notification, set a group key and create a
                // summary notification via
                // .setGroupSummary(true)
                // .setGroup(GROUP_KEY_YOUR_NAME_HERE)
                // Number of new notifications for API <24 (M and below) devices.
                .setSubText(messagingStyleCommsAppData.numberOfNewMessages.toString())
                .addAction(replyAction)
                .setCategory(Notification.CATEGORY_MESSAGE) // Sets priority for 25 and below. For 26 and above, 'priority' is deprecated for
                // 'importance' which is set in the NotificationChannel. The integers representing
                // 'priority' are different from 'importance', so make sure you don't mix them.
                .setPriority(messagingStyleCommsAppData.priority) // Sets lock-screen visibility for 25 and below. For 26 and above, lock screen
                // visibility is set in the NotificationChannel.
                .setVisibility(messagingStyleCommsAppData.channelLockscreenVisibility)

        // If the phone is in "Do not disturb" mode, the user may still be notified if the
        // sender(s) are in a group allowed through "Do not disturb" by the user.
        for (name in messagingStyleCommsAppData.participants) {
            notificationCompatBuilder.addPerson(name)
        }
        return notificationCompatBuilder
    }

    companion object {
        const val ACTION_REPLY = "com.example.android.wearable.wear.wearnotifications.handlers.action.REPLY"
        const val EXTRA_REPLY = "com.example.android.wearable.wear.wearnotifications.handlers.extra.REPLY"
    }
}