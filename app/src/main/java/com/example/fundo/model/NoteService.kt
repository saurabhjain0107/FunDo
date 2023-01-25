package com.example.fundo.model

import android.widget.Toast
import com.example.fundo.MyDb.MyDBHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class NoteService(val dbHelper : MyDBHelper){
    private var auth: FirebaseAuth = Firebase.auth
    private var databaseReference: FirebaseFirestore = FirebaseFirestore.getInstance()
    val noteList = ArrayList<Notes>()

    fun userNotes(notes: Notes, listener: (NoteListener) -> Unit) {
        val uid = auth.currentUser?.uid.toString()
        val docRef = databaseReference.collection("User").document(uid).collection("Notes")
            .document()
        notes.id = docRef.id
        docRef.set(notes).addOnCompleteListener {
            if (it.isSuccessful) {
                var userNote: HashMap<String, String> = HashMap<String, String>()
                userNote.put("title",notes.title)
                userNote.put("subTitle",notes.subTitle)
                userNote.put("notes",notes.notes)
                userNote.put("id",notes.id)
                listener(NoteListener(true, "note saved"))
                docRef.set(userNote)
                dbHelper.sqlInsertData(notes,listener)
            } else {
                listener(NoteListener(false, "Please enter note"))
            }
        }
    }
    fun updateNote(note : Notes,listener: (NoteListener) -> Unit) {
        val uid = auth.currentUser?.uid.toString()
        val noteId = note.id
        val docRef = databaseReference.collection("User").document(uid).collection("Notes").document(noteId)
        var updateNote: HashMap<String, Any> = HashMap<String, Any>()
        updateNote.put("title",note.title)
        updateNote.put("subTitle",note.subTitle)
        updateNote.put("notes",note.notes)
        updateNote.put("id",note.id)
        docRef.set(updateNote).addOnSuccessListener {
            listener(NoteListener(true, "note updated"))
            dbHelper.updateNote(note,listener)
        }
    }
}