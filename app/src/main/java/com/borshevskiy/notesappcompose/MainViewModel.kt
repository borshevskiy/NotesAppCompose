package com.borshevskiy.notesappcompose

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.borshevskiy.notesappcompose.model.Note
import com.borshevskiy.notesappcompose.utils.TYPE_FIREBASE
import com.borshevskiy.notesappcompose.utils.TYPE_ROOM
import java.lang.IllegalArgumentException

class MainViewModel(application: Application): AndroidViewModel(application) {

    val readTest: MutableLiveData<List<Note>> by lazy {
        MutableLiveData<List<Note>>()
    }

    val dbType: MutableLiveData<String> by lazy {
        MutableLiveData<String>(TYPE_ROOM)
    }

    init {
        readTest.value = when(dbType.value) {
            TYPE_ROOM -> listOf(Note(title = "Note 1", description = "Some description"),
                                Note(title = "Note 2", description = "Some description"),
                                Note(title = "Note 3", description = "Some description"),
                                Note(title = "Note 4", description = "Some description"))
            TYPE_FIREBASE -> listOf()
            else -> listOf()
        }
    }

    fun initDatabase(type: String) {
        dbType.value = type
        Log.d("checkDATA", "MainViewModel initDatabase with type $type")
    }

}

class MainViewModelFactory(private val application: Application): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}