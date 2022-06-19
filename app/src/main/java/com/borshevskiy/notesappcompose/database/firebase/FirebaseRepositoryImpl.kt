package com.borshevskiy.notesappcompose.database.firebase

import androidx.lifecycle.LiveData
import com.borshevskiy.notesappcompose.database.DatabaseRepository
import com.borshevskiy.notesappcompose.model.Note
import com.borshevskiy.notesappcompose.utils.Constants.Keys.DESC
import com.borshevskiy.notesappcompose.utils.Constants.Keys.TITLE
import com.borshevskiy.notesappcompose.utils.FIREBASE_ID
import com.borshevskiy.notesappcompose.utils.LOGIN
import com.borshevskiy.notesappcompose.utils.PASSWORD
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirebaseRepositoryImpl: DatabaseRepository {

    private val mAuth = FirebaseAuth.getInstance()
    private val database = Firebase.database.reference.child(mAuth.currentUser?.uid.toString())

    override val readAll: LiveData<List<Note>> = AllNotesLiveData()

    override suspend fun create(note: Note, onSuccess: () -> Unit) {
        val noteId = database.push().key.toString()
        val mapNotes = hashMapOf<String, Any>()
        mapNotes[FIREBASE_ID] = noteId
        mapNotes[TITLE] = note.title
        mapNotes[DESC] = note.description
        database.child(noteId).updateChildren(mapNotes).addOnSuccessListener { onSuccess() }
            .addOnFailureListener { }
    }

    override suspend fun update(note: Note, onSuccess: () -> Unit) {
        val noteId = note.firebaseId
        val mapNotes = hashMapOf<String, Any>()
        mapNotes[FIREBASE_ID] = noteId
        mapNotes[TITLE] = note.title
        mapNotes[DESC] = note.description
        database.child(noteId).updateChildren(mapNotes).addOnSuccessListener { onSuccess() }
            .addOnFailureListener { }
    }

    override suspend fun delete(note: Note, onSuccess: () -> Unit) {
        database.child(note.firebaseId).removeValue().addOnSuccessListener { onSuccess() }
            .addOnFailureListener { }
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