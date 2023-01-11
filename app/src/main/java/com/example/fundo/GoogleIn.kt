package com.example.fundo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.fundo.view.HomePage
import com.example.fundo.view.RegistrationFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class GoogleIn : RegistrationFragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient :GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireContext(),gso)

        binding.google.setOnClickListener {
            signInGoogle()
        }




//        var signInRequest = BeginSignInRequest.builder()
//            .setGoogleIdTokenRequestOptions(
//                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
//                    .setSupported(true)
//                    // Your server's client ID, not your Android client ID.
//                    .setServerClientId(getString(R.string.google_app_id))
//                    // Only show accounts previously used to sign in.
//                    .setFilterByAuthorizedAccounts(true)
//                    .build()
//            )
//            .build()
    }

    private fun signInGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result ->
        if(result.resultCode == Activity.RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResult(task)
        }
    }

    private fun handleResult(task: Task<GoogleSignInAccount>) {

        if(task.isSuccessful){
            val account : GoogleSignInAccount? = task.result
            if(account != null){
                updateId(account)
            }

        }else{
            Toast.makeText(requireContext(),task.exception.toString(), Toast.LENGTH_SHORT).show()
        }

    }

    private fun updateId(account: GoogleSignInAccount) {

        val credential = GoogleAuthProvider.getCredential(account.idToken,null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if(it.isSuccessful){
                val intent :Intent = Intent(requireContext(), HomePage::class.java)
                startActivity(intent)

            }else{
                Toast.makeText(requireContext(),it.exception.toString(),Toast.LENGTH_SHORT).show()
            }
        }

    }
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        val REQ_ONE_TAP = null
//        when (requestCode) {
//            REQ_ONE_TAP -> {
//                try {
//
//                    val oneTapClient = null
////                    val credential = oneTapClient.getSignInCredentialFromIntent(data)
//                    val credential = GoogleAuthProvider.getCredential(oneTapClient, null)
//                    val idToken = credential.signInMethod
//                    when {
//                        idToken != null -> {
//                            // Got an ID token from Google. Use it to authenticate
//                            // with Firebase.
//                            Log.d(TAG, "Got ID token.")
//                        }
//                        else -> {
//                            // Shouldn't happen.
//                            Log.d(TAG, "No ID token!")
//                        }
//                    }
//                } catch (e: ApiException) {
//                    // ...
//                }
//            }
//        }
//
//
////        val googleCredential = oneTapClient.getSignInCredentialFromIntent(data)
//        val googleCredential = GoogleAuthProvider.getCredential(null,null)
//        val idToken = googleCredential.signInMethod
//        when {
//            idToken != null -> {
//                // Got an ID token from Google. Use it to authenticate
//                // with Firebase.
//                val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
//                auth.signInWithCredential(firebaseCredential).addOnCompleteListener  { task ->
//                        if (task.isSuccessful) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithCredential:success")
//                            val user = auth.currentUser
//                            updateUI(user)
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithCredential:failure", task.exception)
//                            updateUI(null)
//                        }
//                    }
//            }
//            else -> {
//                // Shouldn't happen.
//                Log.d(TAG, "No ID token!")
//            }
//        }
//    }
}

