package com.example.fundo.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.fundo.MyDb.MyDBHelper
import com.example.fundo.databinding.FragmentNotesBinding
import com.example.fundo.model.NoteService
import com.example.fundo.model.Notes
import com.example.fundo.viewmodel.NotesViewModel
import com.example.fundo.viewmodel.NotesViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class NotesFragment : Fragment() {
    private var auth: FirebaseAuth = Firebase.auth
    private var databaseReference: FirebaseFirestore = FirebaseFirestore.getInstance()
    lateinit var notesViewModel: NotesViewModel
    lateinit var binding : FragmentNotesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotesBinding.inflate(layoutInflater)

//        val connectionManager : Any = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val activeNetwork: NetworkInfo = connectionManager as NetworkInfo
//        val isConnected : Boolean = activeNetwork.isConnectedOrConnecting == true


        binding.addNote.setOnClickListener {
//            if (isConnected) {
                auth = FirebaseAuth.getInstance()
                notesViewModel = ViewModelProvider(
                    this,
                    NotesViewModelFactory(NoteService(MyDBHelper(requireContext())))
                ).get(NotesViewModel::class.java)
                val title = binding.title.text.toString()
                val subTitle = binding.subTitle.text.toString()
                val note = binding.notes.text.toString()
                var notes = Notes(id = "", title = title, subTitle = subTitle, notes = note)
                if (!note.equals("")) {
                    notesViewModel.userNotes(notes)
                    notesViewModel.userNotesStatus.observe(viewLifecycleOwner, Observer {
                        if (it.status) {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                            val intent = Intent(requireContext(), HomePage::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }
                    })
                } else {
                    Toast.makeText(requireContext(), "Please enter note", Toast.LENGTH_SHORT).show()
                }
//            }else{
//                val db = MyDBHelper(requireContext())
//
//
//            }
        }
        binding.dismiss.setOnClickListener {
            val intent = Intent(requireContext(), HomePage::class.java)
            startActivity(intent)
        }
        return binding.root
    }
}
