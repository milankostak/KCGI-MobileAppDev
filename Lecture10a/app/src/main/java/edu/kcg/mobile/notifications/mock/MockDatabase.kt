package edu.kcg.mobile.notifications.mock

import android.app.NotificationManager
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.core.app.NotificationCompat
import androidx.core.app.Person
import androidx.core.graphics.drawable.IconCompat
import edu.kcg.mobile.notifications.R

/**
 * Mock data for each of the Notification Style Demos.
 */
object MockDatabase {

    fun getMessagingStyleData(context: Context): MessagingStyleCommsAppData {
        return MessagingStyleCommsAppData.getInstance(context)
    }

    fun resourceToUri(context: Context, resId: Int): Uri {
        return Uri.parse(
                ContentResolver.SCHEME_ANDROID_RESOURCE
                        + "://"
                        + context.resources.getResourcePackageName(resId)
                        + "/"
                        + context.resources.getResourceTypeName(resId)
                        + "/"
                        + context.resources.getResourceEntryName(resId))
    }

    class MessagingStyleCommsAppData(context: Context) : MockNotificationData() {
        // Unique data for this Notification.Style:
        val messages: MutableList<NotificationCompat.MessagingStyle.Message> = mutableListOf()

        // String of all mMessages.
        private val fullConversation: String

        // Name preferred when replying to chat.
        val me: Person
        val participants: MutableList<Person> = mutableListOf()
        val replyChoicesBasedOnLastMessage: Array<CharSequence>
        val numberOfNewMessages: Int
            get() = messages.size

        override fun toString(): String {
            return fullConversation
        }

        val isGroupConversation: Boolean
            get() = participants.size > 1

        companion object {

            private var sInstance: MessagingStyleCommsAppData? = null

            fun getInstance(context: Context): MessagingStyleCommsAppData {
                return sInstance ?: getSync(context).also {
                    sInstance = it
                }
            }

            @Synchronized
            private fun getSync(context: Context): MessagingStyleCommsAppData {
                return sInstance ?: MessagingStyleCommsAppData(context).also {
                    sInstance = it
                }
            }
        }

        init {
            // Standard notification values:
            // Content for API <24 (M and below) devices.
            // Note: I am actually hardcoding these Strings based on info below. You would be
            // pulling these values from the same source in your database. I leave this up here, so
            // you can see the standard parts of a Notification first.
            contentTitle = "3 Messages w/ Famous, Wendy"
            contentText = "HEY, I see my house! :)"
            priority = NotificationCompat.PRIORITY_HIGH

            // Create the users for the conversation.
            // Name preferred when replying to chat.
            me = Person.Builder()
                    .setName("Me MacDonald")
                    .setKey("1234567890")
                    .setUri("tel:1234567890")
                    .setIcon(IconCompat.createWithResource(context, R.drawable.me_macdonald))
                    .build()
            val participant1 = Person.Builder()
                    .setName("Famous Frank")
                    .setKey("9876543210")
                    .setUri("tel:9876543210")
                    .setIcon(IconCompat.createWithResource(context, R.drawable.famous_fryer))
                    .build()
            val participant2 = Person.Builder()
                    .setName("Wendy Weather")
                    .setKey("2233221122")
                    .setUri("tel:2233221122")
                    .setIcon(IconCompat.createWithResource(context, R.drawable.wendy_wonda))
                    .build()

            // If the phone is in "Do not disturb mode, the user will still be notified if
            // the user(s) is starred as a favorite.
            // Note: You don't need to add yourself, aka 'me', as a participant.
            participants.add(participant1)
            participants.add(participant2)

            // For each message, you need the timestamp. In this case, we are using arbitrary longs
            // representing time in milliseconds.
            messages.add( // When you are setting an image for a message, text does not display.
                    NotificationCompat.MessagingStyle.Message("", 1528490641998L, participant1)
                            .setData("image/png", resourceToUri(context, R.drawable.earth))
            )
            messages.add(
                    NotificationCompat.MessagingStyle.Message("Visiting the moon again? :P", 1528490643998L, me)
            )
            messages.add(
                    NotificationCompat.MessagingStyle.Message("HEY, I see my house!", 1528490645998L, participant2)
            )

            // String version of the mMessages above.
            fullConversation = """
                Famous: [Picture of Moon]
                
                Me: Visiting the moon again? :P
                
                Wendy: HEY, I see my house! :)
                """.trimIndent()
            replyChoicesBasedOnLastMessage = arrayOf("Me too!", "How's the weather?", "You have good eyesight.")

            // Notification channel values (for devices targeting 26 and above):
            channelId = "channel_messaging_1"
            // The user-visible name of the channel.
            channelName = "Complex Messaging"
            // The user-visible description of the channel.
            channelDescription = "Sample Messaging Notifications"
            channelImportance = NotificationManager.IMPORTANCE_MAX
            isChannelEnableVibrate = true
            channelLockscreenVisibility = NotificationCompat.VISIBILITY_PRIVATE
        }
    }

    /**
     * Represents standard data needed for a Notification.
     */
    abstract class MockNotificationData {
        // Notification Standard notification get methods:
        // Standard notification values:
        var contentTitle: String? = null
        var contentText: String? = null
        var priority = 0

        // Channel values (O and above) get methods:
        // Notification channel values (O and above):
        var channelId: String? = null
        var channelName: CharSequence? = null
        var channelDescription: String? = null
        var channelImportance = 0
        var isChannelEnableVibrate = false
        var channelLockscreenVisibility = 0
    }
}