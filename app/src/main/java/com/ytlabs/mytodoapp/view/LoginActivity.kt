package com.ytlabs.mytodoapp.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ytlabs.mytodoapp.R
import com.ytlabs.mytodoapp.utils.AppConstant
import com.ytlabs.mytodoapp.utils.PrefConstant
import com.ytlabs.mytodoapp.utils.StoreSession

class LoginActivity : AppCompatActivity() {
    lateinit var editTextFullName: EditText
    lateinit var editTextUserName: EditText
    lateinit var buttonLogin: Button

    private val TAG = "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        editTextFullName = findViewById(R.id.editTextFullName)
        editTextUserName = findViewById(R.id.editTextUserName)
        buttonLogin = findViewById(R.id.buttonLogin)

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
        StoreSession.write(PrefConstant.FULL_NAME, fullName)
    }

    private fun saveLoginStatus() {
        StoreSession.write(PrefConstant.IS_LOGGED_IN, true)
    }

}