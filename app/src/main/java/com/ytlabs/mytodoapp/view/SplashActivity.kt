package com.ytlabs.mytodoapp.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ytlabs.mytodoapp.utils.PrefConstant
import com.ytlabs.mytodoapp.R

class SplashActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        setupSharedPreference()
        checkLoginStatus()
    }

    private fun setupSharedPreference() {
        sharedPreferences =
            getSharedPreferences(PrefConstant.SHARED_PREFRENCE_NAME, Context.MODE_PRIVATE)
    }

    fun checkLoginStatus() {
        val isLoggedIn = sharedPreferences.getBoolean(PrefConstant.IS_LOGGED_IN, false)
        if (isLoggedIn) {
            startActivity(Intent(this, MyNotesActivity::class.java))

        } else {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}