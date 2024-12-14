package mikhail.shell.bank.app.domain.services

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.messaging

class NotificationService : FirebaseMessagingService() {
    private val _tag = "NotificationService"
    private val _firebaseMessaging = Firebase.messaging
    override fun onMessageReceived(message: RemoteMessage) {
        Log.i(_tag, message.data.toString())
    }
}