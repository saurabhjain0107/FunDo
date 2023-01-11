package com.example.fundo.view
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.fundo.R
import com.example.fundo.databinding.FragmentLoginBinding
import com.example.fundo.model.AuthService
import com.example.fundo.model.User
import com.example.fundo.viewmodel.LoginViewModel
import com.example.fundo.viewmodel.LoginViewModelFactory
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginFragment : Fragment(){
    lateinit var binding: FragmentLoginBinding
    private lateinit var  firebaseAuth : FirebaseAuth
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient : GoogleSignInClient
    private val REQ_ONE_TAP = 2  // Can be any integer unique to the Activity
    private var showOneTapUI = true
    private lateinit var signInRequest: BeginSignInRequest
    private val RC_SIGN_IN = 100
    lateinit var loginViewModel: LoginViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater,container,false)
        auth = Firebase.auth
        loginViewModel = ViewModelProvider(this,LoginViewModelFactory(AuthService())).get(LoginViewModel::class.java)

        binding.RegistrationFrag.setOnClickListener {
            val transaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container, RegistrationFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }
        binding.loginbtn.setOnClickListener {
            val mEmail = binding.IdInput.text.toString()
            val mPassword = binding.editTextTextPassword.text.toString()

            var user = User(email = mEmail,password = mPassword)
            loginViewModel.userLogin(user)
            loginViewModel.userLoginStatus.observe(viewLifecycleOwner, Observer {
                if(it.status){

                    updateUI()
                    Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()


                }else{
                    Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()
                }
            })

        }

        binding.forgetPassword.setOnClickListener {

            val transaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container, ForgetPassword())
            transaction.addToBackStack(null)
            transaction.commit()


        }

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireContext(),googleSignInOptions)

        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()
        binding.google.setOnClickListener {
            Log.d(TAG, "onCreateView: begin Google SignIn")
            val intent = googleSignInClient.signInIntent
            startActivityForResult(intent,RC_SIGN_IN)
        }

        return binding.root
    }

    private fun checkUser() {



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RC_SIGN_IN){
            Log.d(TAG, "onActivityResult: Google SignIn intent result")
            val accountTask = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = accountTask.getResult(ApiException::class.java)
                firebaseAuthWithGoogleAccount(account)
            }catch (e: Exception){
                Log.d(TAG, "onActivityResult: $(e.message)")

            }
        }
    }

    private fun firebaseAuthWithGoogleAccount(account: GoogleSignInAccount?) {

        Log.d(TAG, "firebaseAuthWithGoogleAccount: begin firebase auth  with google account")

        val credential = GoogleAuthProvider.getCredential(account?.idToken,null)
        firebaseAuth.signInWithCredential(credential)
            .addOnSuccessListener { authResult ->
                Log.d(TAG, "firebaseAuthWithGoogleAccount: LoggedIn")

                val firebaseUser = firebaseAuth.currentUser
                val uid = firebaseUser!!.uid
                val email = firebaseUser!!.email
                Log.d(TAG, "firebaseAuthWithGoogleAccount: uid: $uid")
                Log.d(TAG, "firebaseAuthWithGoogleAccount: Email: $email")

                if(authResult.additionalUserInfo!!.isNewUser){
                    Log.d(TAG, "firebaseAuthWithGoogleAccount: Account created ..\n$email")
                    Toast.makeText(requireContext(),"Account created...\n$email",Toast.LENGTH_SHORT).show()
                }
                else{
                    Log.d(TAG, "firebaseAuthWithGoogleAccount: Existing user...\n$email")
                    Toast.makeText(requireContext(),"LoggedIn...\n$email",Toast.LENGTH_SHORT).show()
                }
                startActivity(Intent(requireContext(), MainActivity::class.java))


            }
            .addOnFailureListener {e ->
                Log.d(TAG, "firebaseAuthWithGoogleAccount: Loggin Failed due to $(e.message)")
                Toast.makeText(requireContext(),"Loggin Failed due to \$(e.message)",Toast.LENGTH_SHORT).show()
            }

    }
    private fun updateUI(): Any {
        val intent = Intent(requireContext(), HomePage::class.java)
        return startActivity(intent)

    }
}

