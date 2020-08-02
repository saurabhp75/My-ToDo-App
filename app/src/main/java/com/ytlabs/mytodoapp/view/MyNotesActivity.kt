package com.ytlabs.mytodoapp.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ytlabs.mytodoapp.NotesApp
import com.ytlabs.mytodoapp.utils.AppConstant
import com.ytlabs.mytodoapp.utils.PrefConstant
import com.ytlabs.mytodoapp.R
import com.ytlabs.mytodoapp.adaper.NotesAdapter
import com.ytlabs.mytodoapp.clicklisteners.ItemClickListener
import com.ytlabs.mytodoapp.db.Notes

//import com.ytlabs.mytodoapp.model.Notes

class MyNotesActivity : AppCompatActivity() {
    private val TAG = "MyNotesActivity"
    private var fullName: String? = null
    private lateinit var fabAddNotes: FloatingActionButton
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var recyclerViewNotes: RecyclerView
    val ADD_NOTES_CODE = 100

    var notesList = mutableListOf<Notes>()
//    var notesList = ArrayList<Notes>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_notes)

        // Bind views to the variables
        bindView()

        fabAddNotes.setOnClickListener {
//            setupDialogBox()
            val intent = Intent(this@MyNotesActivity, AddNotesActivity::class.java)
            startActivityForResult(intent, ADD_NOTES_CODE)
        }

        setupSharedPreferences()
        getIntentData()
        getDataFromDb()
        supportActionBar?.title = fullName
        setupRecyclerView()
        // Code below doesn't work
        // actionBar?.title = fullName
    }

    private fun getDataFromDb() {
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getNotesDb().notesDao()
        notesList.addAll(notesDao.getAll())
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
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes)
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

            if (!title.isEmpty() && !description.isEmpty()) {
                val note = Notes(title = title, description = description)
                notesList.add(note)
                addNoteToDb(note)
            } else {
                Toast.makeText(
                    this@MyNotesActivity,
                    "Title or description can't be empty",
                    Toast.LENGTH_SHORT
                ).show()
            }
            dialog.hide()

        }
    }

    private fun addNoteToDb(note: Notes) {
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getNotesDb().notesDao()

        notesDao.insert(note)

    }

    private fun setupRecyclerView() {
        val itemClickListener = object : ItemClickListener {
            override fun onClick(notes: Notes) {
//                Log.d(TAG, "${note.title}");
                val detailIntent = Intent(this@MyNotesActivity, DetailActivity::class.java)
                detailIntent.putExtra(AppConstant.TITLE, notes.title)
                detailIntent.putExtra(AppConstant.DESCRIPTION, notes.description)
                startActivity(detailIntent)
//                Intent(this, DetailActivity::class.java)

            }

            override fun onUpdate(notes: Notes) {
                val notesApp = applicationContext as NotesApp
                val notesDao = notesApp.getNotesDb().notesDao()
                notesDao.updateNotes(notes)
            }
        }

        val notesAdapter = NotesAdapter(notesList, itemClickListener)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerViewNotes.layoutManager = linearLayoutManager
        recyclerViewNotes.adapter = notesAdapter
    }
}