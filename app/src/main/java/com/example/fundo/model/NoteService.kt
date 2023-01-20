package com.example.fundo.model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class NoteService {
    private var auth: FirebaseAuth = Firebase.auth
    private var databaseReference: FirebaseFirestore = FirebaseFirestore.getInstance()
    val noteList = ArrayList<Notes>()
    fun getNote(listener: NoteListener) {
        val note = ArrayList<Notes>()
        val uid = auth.currentUser?.uid.toString()
        val docRef = databaseReference.collection("User").document(uid).collection("Notes")
            .document()
//        notes.id = docRef.id
        docRef.get().addOnCompleteListener { document ->
            if (document.isSuccessful) {
//             for(var documents: document ){
//
//             }
//                listener(NoteListener(true, "note saved"))
//
//
//            } else {
//                listener(AuthListener(false, "Please enter note"))
//
//            }
            }

        }
    }

    fun userNotes(notes: Notes, listener: (NoteListener) -> Unit) {

        val uid = auth.currentUser?.uid.toString()
        val docRef = databaseReference.collection("User").document(uid).collection("Notes")
            .document()
        notes.id = docRef.id
        docRef.get().addOnCompleteListener {
            if (it.isSuccessful) {

                var userNote: HashMap<String, String> = HashMap<String, String>()
                userNote.put("title",notes.title)
                userNote.put("subTitle",notes.subTitle)
                userNote.put("notes",notes.notes)
                userNote.put("id",notes.id)
                listener(NoteListener(true, "note saved"))
                docRef.set(userNote)


            } else {
                listener(NoteListener(false, "Please enter note"))

            }
        }
    }
}