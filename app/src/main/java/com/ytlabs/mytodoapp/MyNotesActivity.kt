package com.ytlabs.mytodoapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ytlabs.mytodoapp.R
import com.ytlabs.mytodoapp.adaper.NotesAdapter
import com.ytlabs.mytodoapp.clicklisteners.ItemClickListener
import com.ytlabs.mytodoapp.model.Notes
import java.util.ArrayList

class MyNotesActivity : AppCompatActivity() {
    private val TAG = "MyNotesActivity"
    private var fullName: String? = null
    lateinit var fabAddNotes: FloatingActionButton
    lateinit var sharedPreferences: SharedPreferences
    lateinit var recyclerViewNotes: RecyclerView

    var notesList = mutableListOf<Notes>()
//    var notesList = ArrayList<Notes>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_notes)

        // Bind views to the variables
        bindView()

        fabAddNotes.setOnClickListener {
            setupDialogBox()
        }

        setupSharedPreferences()
        getIntentData()
        supportActionBar?.title = fullName
        // Code below doesn't work
        // actionBar?.title = fullName
    }

    private fun setupSharedPreferences() {
        sharedPreferences =
            getSharedPreferences(PrefConstant.SHARED_PREFRENCE_NAME, Context.MODE_PRIVATE)
    }

    private fun getIntentData() {
        fullName = intent.getStringExtra(AppConstant.FULL_NAME)
        if (fullName == null) {
            fullName = sharedPreferences.getString(PrefConstant.FULL_NAME, "")
        }
    }

    private fun bindView() {
        fabAddNotes = findViewById(R.id.fabAddNotes)
        recyclerViewNotes = findViewById(R.id.recylclerViewNotes)
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
            val title = editTextTitle.text.toString()
            val description = editTextDescription.text.toString()

            if(!title.isEmpty() && !description.isEmpty()){
                val notes = Notes(title, description)
                notesList.add(notes)
            } else {
                Toast.makeText(this@MyNotesActivity, "Title or description can't be empty", Toast.LENGTH_SHORT).show()
            }
            setupRecyclerView()
            dialog.hide()

        }
    }

    private fun setupRecyclerView() {
        val itemClickListener = object : ItemClickListener {
            override fun onClick(note: Notes) {
//                Log.d(TAG, "${note.title}");
                val detailIntent = Intent(this@MyNotesActivity, DetailActivity::class.java)
                detailIntent.putExtra(AppConstant.TITLE, note.title)
                detailIntent.putExtra(AppConstant.DESCRIPTION, note.description)
                startActivity(detailIntent)
//                Intent(this, DetailActivity::class.java)

            }
        }

        val notesAdapter = NotesAdapter(notesList, itemClickListener)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerViewNotes.layoutManager = linearLayoutManager
        recyclerViewNotes.adapter = notesAdapter
    }
}