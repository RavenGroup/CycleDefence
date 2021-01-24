package com.example.prototype

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class Firebase : FirebaseMessagingService() {
    private val TAG: String = "Firebase"

    init {
        Log.d("$TAG/firebase", "my name is $TAG")
    }

    override fun onNewToken(token: String) {
//        super.onNewToken(token)
        Log.d(TAG, "New token: $token")
//        sendRegistrationToServer(refreshedToken)
    }

    /*
        override fun onMessageReceived(msg: RemoteMessage) {
            Log.d(TAG + "onMessageReceived", msg.toString())
        }
        */
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // ...

        // TODO: Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.from)

        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: " + remoteMessage.data)
            if ( /* Check if data needs to be processed by long running job */true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
//                scheduleJob()
                Log.d("$TAG/data", remoteMessage.data.toString())
            } else {
                // Handle message within 10 seconds
//                handleNow()
                Log.d("$TAG/dataNotLong", remoteMessage.data.toString())

            }
        }

        // Check if message contains a notification payload.
        if (remoteMessage.notification != null) {
            Log.d(
                TAG,
                "Message Notification Body: " + remoteMessage.notification!!.body
            )
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
}