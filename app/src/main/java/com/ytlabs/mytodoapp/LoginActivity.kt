package com.ytlabs.mytodoapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.edit

class LoginActivity : AppCompatActivity() {
    lateinit var editTextFullName: EditText
    lateinit var editTextUserName: EditText
    lateinit var buttonLogin: Button
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    private val TAG = "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        editTextFullName = findViewById(R.id.editTextFullName)
        editTextUserName = findViewById(R.id.editTextUserName)
        buttonLogin = findViewById(R.id.buttonLogin)

        setupSharedPreferences()

        buttonLogin.setOnClickListener {
            val fullName = editTextFullName.text.toString()
            val userName = editTextUserName.text.toString()

            if (fullName.isNotEmpty() and userName.isNotEmpty()) {
                val intent = Intent(this, MyNotesActivity::class.java)
                intent.putExtra(AppConstant.FULL_NAME, fullName)
                intent.putExtra(AppConstant.USER_NAME, userName)
                startActivity(intent)
                saveLoginStatus()
                saveFullName(fullName)
            } else {
                Toast.makeText(this, "User Name can't be Empty", Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun saveFullName(fullName: String) {
        editor = sharedPreferences.edit()
        editor.putString(PrefConstant.FULL_NAME, fullName)
        editor.apply()
    }

    private fun saveLoginStatus() {
        editor = sharedPreferences.edit()
        editor.putBoolean(PrefConstant.IS_LOGGED_IN, true)
        editor.apply()
    }

    private fun setupSharedPreferences() {
        //This shared prefrence shall be accessible by this app only
        sharedPreferences = getSharedPreferences(PrefConstant.SHARED_PREFRENCE_NAME, Context.MODE_PRIVATE)
    }
}