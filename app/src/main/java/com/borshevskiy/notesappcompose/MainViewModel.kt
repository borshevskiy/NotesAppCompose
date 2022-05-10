package com.borshevskiy.notesappcompose

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.borshevskiy.notesappcompose.database.room.AppRoomDatabase
import com.borshevskiy.notesappcompose.database.room.RoomRepositoryImpl
import com.borshevskiy.notesappcompose.model.Note
import com.borshevskiy.notesappcompose.navigation.NavRoute
import com.borshevskiy.notesappcompose.utils.REPOSITORY
import com.borshevskiy.notesappcompose.utils.TYPE_ROOM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {

    val context = application

    fun initDatabase(type: String, onSuccess: () -> Unit) {
        Log.d("checkDATA", "MainViewModel initDatabase with type $type")
        when(type) {
            TYPE_ROOM -> {
                REPOSITORY = RoomRepositoryImpl(AppRoomDatabase.getInstance(context).getRoomDao())
                onSuccess()
            }
        }
    }

    fun addNote(note: Note, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.create(note) {
                viewModelScope.launch(Dispatchers.Main) { onSuccess() }
            }
        }
    }

    fun readAllNotes() = REPOSITORY.readAll

}

class MainViewModelFactory(private val application: Application): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}