package com.ytlabs.mytodoapp.db

//import android.os.FileObserver.DELETE
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface NotesDao {

    @Query("SELECT * from notesData")
    fun getAll(): MutableList<Notes>

    @Insert(onConflict = REPLACE)
    fun insert(notes: Notes)

    @Update
    fun updateNotes(notes: Notes)

    @Delete
    fun delete(notes: Notes)

    @Query("DELETE FROM notesData WHERE isTaskCompleted = :status")
    fun deleteNotes(status: Boolean)
}