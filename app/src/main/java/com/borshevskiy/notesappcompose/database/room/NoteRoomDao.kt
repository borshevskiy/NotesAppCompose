package com.borshevskiy.notesappcompose.database.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.borshevskiy.notesappcompose.model.Note

@Dao
interface NoteRoomDao {

    @Query("SELECT * FROM notes_table")
    fun getAllNotes(): LiveData<List<Note>>

    @Insert
    suspend fun addNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)


    @Delete
    suspend fun deleteNote(note: Note)
}