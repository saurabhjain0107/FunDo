package com.example.fundo.view

import android.app.DialogFragment
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fundo.R
import com.example.fundo.databinding.FragmentDialogProfileBinding
import com.example.fundo.databinding.FragmentProfileBinding
import com.example.fundo.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import org.w3c.dom.Text


class DialogProfileFragment : androidx.fragment.app.DialogFragment(){
    private var auth: FirebaseAuth = Firebase.auth
    private var databaseReference: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var storage: FirebaseStorage = FirebaseStorage.getInstance()
    private lateinit var selectedImg: Uri

    lateinit var binding: FragmentDialogProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDialogProfileBinding.inflate(inflater, container, false)

        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        startActivityForResult(intent, 1)

        binding.logout.setOnClickListener {

        }
        readProfileData()
//        var user = readProfileData()
//        binding.firstname.text = user.firstName
//        binding.lastname.text = user.lastName
//        binding.email.text = user.email



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

    fun readProfileData(){
        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid!!
        val docRef = databaseReference.collection("User").document(uid)
        docRef.get().addOnSuccessListener {
            if (it.exists()) {
                binding.firstname.text =it.getString("FirstName").toString()
                binding.lastname.text = it.getString("LastName").toString()
                binding.email.text = (it.getString("Email") as Text).toString()
            }
        }
    }

    companion object {


    }
}