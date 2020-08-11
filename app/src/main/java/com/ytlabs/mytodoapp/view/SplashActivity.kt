package com.ytlabs.mytodoapp.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.ytlabs.mytodoapp.R
import com.ytlabs.mytodoapp.onboarding.OnBoardingActivity
import com.ytlabs.mytodoapp.utils.PrefConstant
import com.ytlabs.mytodoapp.utils.StoreSession

class SplashActivity : AppCompatActivity() {
    private val TAG = "SplashActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        checkLoginStatus()
        getFCMToken()

        // Send notification locally from another channel
//        setupNotification("This is local notification")
    }

    /*private fun setupNotification(body: String) {
        // Channel id is required for notifications in Oreo and above
        val channelId = "Local ID"
        val ringtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle("To do notes app")
            .setContentText(body)
            .setSound(ringtone)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Current API version of phone is > Oreo
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(channelId, "Local-Todo-Notes", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())

    }*/

    private fun getFCMToken() {
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = task.result?.token

                // Log and toast
                Log.d(TAG, token.toString())
            })
    }

    private fun checkLoginStatus() {
        val isLoggedIn = StoreSession.read(PrefConstant.IS_LOGGED_IN)
        val isBoardingSuccess = StoreSession.read(PrefConstant.ON_BOARDED_SUCCESSFULLY)
        if (isLoggedIn!!) {
            startActivity(Intent(this, MyNotesActivity::class.java))

        } else {
            // If on boarded success -> login
            // else -> onBoardingActivity
            if (isBoardingSuccess!!) {
                startActivity(Intent(this, LoginActivity::class.java))
            } else {
                startActivity(Intent(this, OnBoardingActivity::class.java))
            }
        }

        // Kill splashActivity
        finish()
    }
}