package com.example.fundo.model

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import com.example.fundo.MyDb.MyDBHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class NoteService( val dbHelper : MyDBHelper){
    private var auth: FirebaseAuth = Firebase.auth
    private var databaseReference: FirebaseFirestore = FirebaseFirestore.getInstance()
    val noteList = ArrayList<Notes>()


    fun userNotes(notes: Notes, listener: (NoteListener) -> Unit) {

            val uid = auth.currentUser?.uid.toString()
            val docRef = databaseReference.collection("User").document(uid).collection("Notes").document()
            notes.id = docRef.id
        if(checkForInternet(dbHelper.context)){
           docRef.set(notes).addOnCompleteListener {
                if (it.isSuccessful) {
                    var userNote: HashMap<String, String> = HashMap<String, String>()
                    userNote.put("title",notes.title)
                    userNote.put("subTitle",notes.subTitle)
                    userNote.put("notes",notes.notes)
                    userNote.put("id",notes.id)
                    userNote.put("isArchive", notes.isArchive.toString())
                    listener(NoteListener(true, "note saved"))
                    dbHelper.sqlInsertData(notes,listener)
                } else {
                    listener(NoteListener(false, "Please enter note"))
                }
            }
        }else{
            dbHelper.sqlInsertData(notes, listener)
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
            dbHelper.updateNote(note,listener)
            listener(NoteListener(true, "note updated"))
        }
    }

    private fun checkForInternet(context: Context): Boolean {

        // register activity with the connectivity manager service
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // if the android version is equal to M
        // or greater we need to use the
        // NetworkCapabilities to check what type of
        // network has the internet connection
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Returns a Network object corresponding to
            // the currently active default data network.
            val network = connectivityManager.activeNetwork ?: return false

            // Representation of the capabilities of an active network.
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                // Indicates this network uses a Wi-Fi transport,
                // or WiFi has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                // Indicates this network uses a Cellular transport. or
                // Cellular has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                // else return false
                else -> false
            }
        } else {
            // if the android version is below M
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }
}