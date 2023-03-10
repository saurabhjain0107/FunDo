package com.example.fundo.view

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.example.fundo.R
import com.example.fundo.databinding.FragmentProfileBinding
import com.example.fundo.model.AuthService
import com.example.fundo.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class ProfileFragment :DialogFragment() {
    private var auth: FirebaseAuth = Firebase.auth
    private var databaseReference: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var storage: FirebaseStorage = FirebaseStorage.getInstance()
    private lateinit var selectedImg: Uri

    lateinit var binding: FragmentProfileBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentProfileBinding.inflate(inflater, container, false)


        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        startActivityForResult(intent, 1)

        binding.logout.setOnClickListener {
           val intent = Intent(requireContext(),LoginFragment::class.java)
            startActivity(intent)
        }


        var user = readProfileData()
        binding.firstname.text = user.firstName
        binding.lastname.text = user.lastName
        binding.email.text = user.email

//        binding.userImg.setOnClickListener {
//            val photoPicker = Intent(Intent.ACTION_PICK)
//            photoPicker.type = "image/*"
//            activityResultLauncher.launch(photoPicker)
//        }


        return binding.root


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data != null){
            if(data.data != null){
                selectedImg = data.data!!
                binding.userImg.setImageURI(selectedImg)
            }
        }
    }


    fun readProfileData():User {


        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid.toString()
        var user = User()

        val docRef = databaseReference.collection("User").document(uid)
        docRef.get().addOnCompleteListener {
            if (it.isSuccessful) {

                 user = User(it.result.getString("FirstName").toString(),it.result.getString("LastName").toString(),it.result.getString("Email").toString())
            }
        }

        return user
    }
}