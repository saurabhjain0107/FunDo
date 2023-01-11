package com.example.fundo.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fundo.R
import com.example.fundo.databinding.FragmentForgetPasswordBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ForgetPassword : Fragment() {

    lateinit var binding: FragmentForgetPasswordBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentForgetPasswordBinding.inflate(inflater,container,false)
        auth = Firebase.auth


        binding.loginAgain.setOnClickListener {

            val transaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container, LoginFragment())
            transaction.addToBackStack(null)
            transaction.commit()

        }

        binding.ResetBtn.setOnClickListener {

            validateData()

        }

        return binding.root
    }

    private fun validateData() {
        val email = binding.email.text.toString()
        if(email.isEmpty()){
            binding.email.setError("Email Required")
        }
        else{
            forgetPass()
        }
    }

    private fun forgetPass() {
        val email = binding.email.text.toString()
        auth.sendPasswordResetEmail(email).addOnCompleteListener {
            if(it.isSuccessful){
                Toast.makeText(requireContext(),"Check Your Email :"+it.exception,Toast.LENGTH_SHORT).show()
                startActivity(Intent(requireContext(),LoginFragment::class.java))

            }else{
                Toast.makeText(requireContext(),"Error"+it.exception,Toast.LENGTH_SHORT).show()
            }
        }
    }
}