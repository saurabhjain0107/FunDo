package com.example.fundo.model

import android.content.ContentValues
import android.content.Intent
import android.location.GnssAntennaInfo.Listener
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import com.example.fundo.view.HomePage
import com.example.fundo.view.ProfileFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
//import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage


class AuthService {
    private var auth: FirebaseAuth = Firebase.auth
    private var databaseReference: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var storageReference: FirebaseStorage




    fun userLogin(user: User,listener: (AuthListener) -> Unit){
        auth.signInWithEmailAndPassword(user.email,user.password).addOnCompleteListener { task ->
            if (task.isSuccessful) {

                listener(AuthListener(true,"Login successful"))

            } else {

                listener(AuthListener(false,"Login Failed"))
            }

        }

     }

    fun loadAllData(uid: String) {



        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid.toString()
        val docRef = databaseReference.collection("User").document(uid)
        docRef.get().addOnCompleteListener {
            if(it.isSuccessful){

                val user = User(it.result.getString("FirstName").toString())
            }
        }

    }

    fun userRegister(user: User,listener: (AuthListener) -> Unit )  {

        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid.toString()


        if(uid != null) {


            auth.createUserWithEmailAndPassword(user.email, user.password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        val docRef = databaseReference.collection("User").document(uid)

                        var userDetail : HashMap<String, String> = HashMap<String, String> ()

                        userDetail.put("FirstName",user.firstName)
                        userDetail.put("LastName",user.lastName)
                        userDetail.put("Email",user.email)
                        userDetail.put("Password",user.password)
                        userDetail.put("Id",uid)


                        listener(AuthListener(true, "Register successfully"))
                        docRef.set(userDetail)


                    } else {

                        listener(AuthListener(false, "Register Unsuccessful"))
                    }
                }
        }
    }

    fun userNotes(notes: Notes, listener: (AuthListener) -> Unit) {

    }
}



