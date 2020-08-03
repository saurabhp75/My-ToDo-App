package com.ytlabs.mytodoapp.workmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.ytlabs.mytodoapp.NotesApp

class MyWorker(context: Context, val workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {
    override fun doWork(): Result {
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getNotesDb().notesDao()
        notesDao.deleteNotes(true)
        return Result.success()
    }

}