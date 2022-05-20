package com.borshevskiy.notesappcompose.database.firebase

import androidx.lifecycle.LiveData
import com.borshevskiy.notesappcompose.database.DatabaseRepository
import com.borshevskiy.notesappcompose.model.Note
import com.borshevskiy.notesappcompose.utils.LOGIN
import com.borshevskiy.notesappcompose.utils.PASSWORD
import com.google.firebase.auth.FirebaseAuth

class FirebaseRepositoryImpl: DatabaseRepository {

    private val mAuth = FirebaseAuth.getInstance()
    override val readAll: LiveData<List<Note>>
        get() = TODO("Not yet implemented")

    override suspend fun create(note: Note, onSuccess: () -> Unit) {
        TODO("Not yet implemented")
    }

    override suspend fun update(note: Note, onSuccess: () -> Unit) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(note: Note, onSuccess: () -> Unit) {
        TODO("Not yet implemented")
    }

    override fun signOut() {
        mAuth.signOut()
    }

    override fun connectToDatabase(onSuccess: () -> Unit, onFail: (String) -> Unit) {
        mAuth.signInWithEmailAndPassword(LOGIN, PASSWORD)
            .addOnCompleteListener { onSuccess() }
            .addOnFailureListener {
                mAuth.createUserWithEmailAndPassword(LOGIN, PASSWORD)
                    .addOnCompleteListener { onSuccess() }
                    .addOnFailureListener { onFail(it.message.toString()) }
            }
    }
}