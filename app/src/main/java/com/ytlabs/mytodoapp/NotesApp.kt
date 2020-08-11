package com.ytlabs.mytodoapp

import android.app.Application
import com.androidnetworking.AndroidNetworking
import com.ytlabs.mytodoapp.db.NotesDatabase
import com.ytlabs.mytodoapp.utils.StoreSession

class NotesApp : Application() {
    override fun onCreate() {
        super.onCreate()

        AndroidNetworking.initialize(applicationContext)

        StoreSession.init(this)
    }

    fun getNotesDb(): NotesDatabase {
        return NotesDatabase.getInstance(this)
    }

}