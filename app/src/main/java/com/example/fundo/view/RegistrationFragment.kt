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
import com.example.fundo.GoogleIn
import com.example.fundo.R
import com.example.fundo.databinding.FragmentRegistrationBinding
import com.example.fundo.model.AuthService
import com.example.fundo.model.User
import com.example.fundo.viewmodel.RegisterViewModel
import com.example.fundo.viewmodel.RegisterViewModelFactory
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase


open class RegistrationFragment : Fragment() {
    lateinit var binding : FragmentRegistrationBinding
    lateinit var registerViewModel: RegisterViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegistrationBinding.inflate(inflater,container,false)
        registerViewModel = ViewModelProvider(this,RegisterViewModelFactory(AuthService())).get(RegisterViewModel::class.java)



        binding.LoginFrag.setOnClickListener {
            val transaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container, LoginFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }
        binding.google.setOnClickListener {
            val intent = Intent(requireContext(), GoogleIn::class.java)
            startActivity(intent)

        }

        binding.btnRegister.setOnClickListener {
            val mEmail = binding.IdInput.text.toString()
            val mPassword = binding.editPassword.text.toString()
            val firstName = binding.firstname.text.toString()
            val lastName = binding.lastname.text.toString()

            var user = User(firstName,lastName,mEmail,mPassword)
            registerViewModel.userRegister(user)
            registerViewModel.userRegisterStatus.observe(viewLifecycleOwner, Observer {
                if(it.status){


                    Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()

                    val transaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.container, LoginFragment())
                    transaction.addToBackStack(null)
                    transaction.commit()


                }
                else{
                    Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()
                }
            })
        }

        return binding.root
    }

    private fun storeData(user: User) {


    }


    fun updateUI(user: FirebaseUser?): Any {

        val intent = Intent(requireContext(), HomePage::class.java)
        return startActivity(intent)
    }
}