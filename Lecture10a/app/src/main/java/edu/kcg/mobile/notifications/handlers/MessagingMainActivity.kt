package edu.kcg.mobile.notifications.handlers

import android.app.Activity
import android.app.NotificationManager
import android.os.Bundle
import edu.kcg.mobile.notifications.MainActivity
import edu.kcg.mobile.notifications.R

/**
 * Template class meant to include functionality for your Messaging App. (This project's main focus
 * is on Notification Styles.)
 */
class MessagingMainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messaging_main)

        // Cancel Notification
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(MainActivity.NOTIFICATION_ID)
    }

}