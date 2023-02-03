package com.example.fundo.view

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fundo.R
import com.example.fundo.databinding.FragmentHomeBinding
import com.example.fundo.model.Notes
import com.example.fundo.viewmodel.NotesViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {
    private var auth: FirebaseAuth = Firebase.auth
    private var databaseReference: FirebaseFirestore = FirebaseFirestore.getInstance()
    lateinit var binding: FragmentHomeBinding
    lateinit var notesViewModel: NotesViewModel
    var noteList = arrayListOf<Notes>()
    lateinit var recyclerview: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.floatingBtn.setOnClickListener {

            val transaction =
                (context as AppCompatActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.drawerLayout, NotesFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteData()
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerview = view.findViewById(R.id.recyclerNote)
        recyclerview.layoutManager = layoutManager
        recyclerview.hasFixedSize()
    }
    private fun noteData() {
        var notes = Notes()
        val tempArrayList = arrayListOf<Notes>()
        val uid = auth.currentUser?.uid.toString()
        val docRef = databaseReference.collection("User").document(uid).collection("Notes")
        docRef.get().addOnCompleteListener {
            if (it.isSuccessful) {
                for (document in it.result) {
                    notes = Notes(
                        document["id"].toString(),
                        document["title"].toString(),
                        document["subTitle"].toString(),
                        document["notes"].toString(),
                    )
                    noteList.add(notes)
                    val adapter = RecyclerNoteAdapter(noteList,requireContext())
                    recyclerview.adapter = adapter
                }
            }
        }
    }
}
