package com.ytlabs.mytodoapp

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ytlabs.mytodoapp.R

class MyNotesActivity : AppCompatActivity() {
    private val TAG = "MyNotesActivity"
    private var fullName: String? = null
    lateinit var fabAddNotes: FloatingActionButton
    lateinit var textViewTitle: TextView
    lateinit var textViewDescription: TextView
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_notes)

        // Bind views to the variables
        bindView()

        fabAddNotes.setOnClickListener {
            setupDialogBox()
        }

        setupSharedPrefereces()
        getIntentData()
        supportActionBar?.title = fullName
        // Code below doesn't work
        // actionBar?.title = fullName
    }

    private fun setupSharedPrefereces() {
        sharedPreferences = getSharedPreferences(PrefConstant.SHARED_PREFRENCE_NAME, Context.MODE_PRIVATE)
    }

    private fun getIntentData() {
        fullName = intent.getStringExtra(AppConstant.FULL_NAME)
        if (fullName == null) {
            fullName = sharedPreferences.getString(PrefConstant.FULL_NAME, "")
        }
    }

    private fun bindView() {
        fabAddNotes = findViewById(R.id.fabAddNotes)
        textViewTitle = findViewById(R.id.textViewTitle)
        textViewDescription = findViewById(R.id.textViewDescription)
    }

    private fun setupDialogBox() {
        val view: View = LayoutInflater.from(this).inflate(R.layout.add_notes_dialog, null)

        val editTextTitle: EditText = view.findViewById(R.id.editTextTitle)
        val editTextDescription: EditText = view.findViewById(R.id.editTextDescription)
        val submitButton: Button = view.findViewById(R.id.buttonSubmit)

        // Dialog
        val dialog = AlertDialog.Builder(this)
            .setView(view)
            .setCancelable(false)
            .create()

        dialog.show()

        submitButton.setOnClickListener {
            textViewTitle.text = editTextTitle.text.toString()
            textViewDescription.text = editTextDescription.text.toString()
            dialog.hide()
        }
    }
}