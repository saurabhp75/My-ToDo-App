package com.ytlabs.mytodoapp.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Constraints
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ytlabs.mytodoapp.NotesApp
import com.ytlabs.mytodoapp.R
import com.ytlabs.mytodoapp.adaper.NotesAdapter
import com.ytlabs.mytodoapp.clicklisteners.ItemClickListener
import com.ytlabs.mytodoapp.db.Notes
import com.ytlabs.mytodoapp.utils.AppConstant
import com.ytlabs.mytodoapp.utils.PrefConstant
import com.ytlabs.mytodoapp.utils.StoreSession
import com.ytlabs.mytodoapp.workmanager.MyWorker
import java.util.concurrent.TimeUnit

//import com.ytlabs.mytodoapp.model.Notes

class MyNotesActivity : AppCompatActivity() {
    private val TAG = "MyNotesActivity"
    private var fullName: String? = null
    private lateinit var fabAddNotes: FloatingActionButton
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

        getIntentData()
        getDataFromDb()
        supportActionBar?.title = fullName
        // Code below doesn't work
        // actionBar?.title = fullName
        setupRecyclerView()
        setupWorkManager()
    }

    private fun setupWorkManager() {
        val constraint = Constraints.Builder()
            .build()
        val request = PeriodicWorkRequest
            .Builder(MyWorker::class.java, 1, TimeUnit.MINUTES)
            .setConstraints(constraint)
            .build()
        WorkManager.getInstance(this).enqueue(request)
        // To run one time task in sequence
        // WorkManager.getInstance().beginWith(request1, request2, request3).then(task).enqueue()
    }

    private fun getDataFromDb() {
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getNotesDb().notesDao()
        notesList.addAll(notesDao.getAll())
    }

    private fun getIntentData() {
        fullName = intent.getStringExtra(AppConstant.FULL_NAME)
        if (fullName == null) {
            fullName = StoreSession.readString(PrefConstant.FULL_NAME)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_NOTES_CODE && resultCode == Activity.RESULT_OK) {
            val title = data?.getStringExtra(AppConstant.TITLE)
            val description = data?.getStringExtra(AppConstant.DESCRIPTION)
            val imagePath = data?.getStringExtra(AppConstant.IMAGE_PATH)

            val note = Notes(
                title = title!!,
                description = description!!,
                imagePath = imagePath!!,
                isTaskCompleted = false
            )
            addNoteToDb(note)
            notesList.add(note)
            recyclerViewNotes.adapter?.notifyItemChanged(notesList.size - 1)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
//        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.blog) {
            val intent = Intent(this@MyNotesActivity, BlogActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}