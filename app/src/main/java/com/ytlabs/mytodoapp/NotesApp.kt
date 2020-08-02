package com.ytlabs.mytodoapp

import android.app.Application
import com.ytlabs.mytodoapp.db.NotesDatabase

class NotesApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }

    fun getNotesDb(): NotesDatabase {
        return NotesDatabase.getInstance(this)
    }

}