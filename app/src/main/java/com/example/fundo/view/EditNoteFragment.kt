package com.example.fundo.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.fundo.MyDb.MyDBHelper
import com.example.fundo.databinding.FragmentEditNoteBinding
import com.example.fundo.model.NoteListener
import com.example.fundo.model.NoteService
import com.example.fundo.model.Notes
import com.example.fundo.viewmodel.NotesViewModel
import com.example.fundo.viewmodel.NotesViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


class EditNoteFragment : Fragment() {
    lateinit var notesViewModel: NotesViewModel
//    val db = MyDBHelper(requireContext())

    lateinit var binding: FragmentEditNoteBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentEditNoteBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        val userId = FirebaseAuth.getInstance().currentUser
        val bundle: Bundle? = arguments
        val noteId = bundle?.getString("noteId").toString()
        val title = bundle?.getString("title")
        val subTitle = bundle?.getString("subTitle")
        val notes = bundle?.getString("notes")
        val edittitle: TextView = binding.editTitle
        edittitle.text = title.toString()
        val editsubTitle: TextView = binding.editSubTitle
        editsubTitle.text = subTitle.toString()
        val editnote: TextView = binding.editNotes
        editnote.text = notes.toString()

        binding.editDismiss.setOnClickListener {
            val intent = Intent(requireContext(), HomePage::class.java)
            startActivity(intent)
        }
        binding.editUpdateNote.setOnClickListener {
            notesViewModel = ViewModelProvider(this,NotesViewModelFactory(NoteService(MyDBHelper(requireContext())))).get(NotesViewModel::class.java)
            val newTitle  = binding.editTitle.text.toString()
            val newsubTitle  = binding.editSubTitle.text.toString()
            val newNote  = binding.editNotes.text.toString()
            var notes = Notes(id = noteId, title = newTitle, subTitle = newsubTitle, notes = newNote)
            notesViewModel.updateNote(notes)
            notesViewModel.userNotesStatus.observe(viewLifecycleOwner, Observer{
                if(it.status){
                    Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()
                    val intent = Intent(requireContext(), HomePage::class.java)
                    startActivity(intent)
                }else {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            })
        }
        return binding.root
    }
}

