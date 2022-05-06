package com.borshevskiy.notesappcompose.database.room

import androidx.lifecycle.LiveData
import com.borshevskiy.notesappcompose.database.DatabaseRepository
import com.borshevskiy.notesappcompose.model.Note

class RoomRepositoryImpl(private val noteRoomDao: NoteRoomDao): DatabaseRepository {

    override val readAll: LiveData<List<Note>>
        get() = noteRoomDao.getAllNotes()

    override suspend fun create(note: Note, onSuccess: () -> Unit) {
        noteRoomDao.addNote(note)
    }

    override suspend fun update(note: Note, onSuccess: () -> Unit) {
        noteRoomDao.updateNote(note)
    }

    override suspend fun delete(note: Note, onSuccess: () -> Unit) {
        noteRoomDao.deleteNote(note)
    }
}