package edu.kcg.mobile.notifications

import android.app.Activity
import android.app.NotificationManager
import android.os.Bundle

class SimpleNotificationActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_notification)

        // Cancel the notification by its ID
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(MainActivity.NOTIFICATION_ID)
    }
}