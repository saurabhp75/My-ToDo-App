package com.ytlabs.mytodoapp.view

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.RingtoneManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.ytlabs.mytodoapp.utils.PrefConstant
import com.ytlabs.mytodoapp.R
import com.ytlabs.mytodoapp.onboarding.OnBoardingActivity

class SplashActivity : AppCompatActivity() {
    private val TAG = "SplashActivity"
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        setupSharedPreference()
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

    private fun setupSharedPreference() {
        sharedPreferences =
            getSharedPreferences(PrefConstant.SHARED_PREFRENCE_NAME, Context.MODE_PRIVATE)
    }

    private fun checkLoginStatus() {
        val isLoggedIn = sharedPreferences.getBoolean(PrefConstant.IS_LOGGED_IN, false)
        val isBoardingSuccess = sharedPreferences.getBoolean(PrefConstant.ON_BOARDED_SUCCESSFULLY, false)
        if (isLoggedIn) {
            startActivity(Intent(this, MyNotesActivity::class.java))

        } else {
            // If on boarded success -> login
            // else -> onBoardingActivity
            if (isBoardingSuccess) {
                startActivity(Intent(this, LoginActivity::class.java))
            } else {
                startActivity(Intent(this, OnBoardingActivity::class.java))
            }
        }
    }
}