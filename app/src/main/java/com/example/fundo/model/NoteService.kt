package com.example.fundo.model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class NoteService {
    private var auth: FirebaseAuth = Firebase.auth
    private var databaseReference: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun userNotes(notes: Notes, listener: (AuthListener) -> Unit) {

        val uid = auth.currentUser?.uid.toString()
        val docRef = databaseReference.collection("User").document(uid).collection("Notes")
            .document()
        notes.id = docRef.id
        docRef.get().addOnCompleteListener {
            if (it.isSuccessful) {
                listener(AuthListener(true, "note saved"))
                val noteList = ArrayList<Notes>()
                noteList.add(notes)


            } else {
                listener(AuthListener(false, "Please enter note"))

            }
        }
    }
}