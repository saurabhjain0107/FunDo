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
import com.example.fundo.databinding.FragmentEditNoteBinding
import com.example.fundo.model.NoteListener
import com.example.fundo.model.Notes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


class EditNoteFragment : Fragment() {
    private var auth: FirebaseAuth = Firebase.auth
    private var databaseReference: FirebaseFirestore = FirebaseFirestore.getInstance()
    val noteList = ArrayList<Notes>()


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
        val noteId = bundle?.getString("noteId")
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
            updatNote(noteId.toString())
            val intent = Intent(requireContext(), HomePage::class.java)
            startActivity(intent)
        }
        return binding.root
    }

    private fun updatNote(noteId : String) {
        val newTitle  = binding.editTitle.text.toString()
        val newsubTitle  = binding.editSubTitle.text.toString()
        val newNote  = binding.editNotes.text.toString()

        val uid = auth.currentUser?.uid.toString()
        val docRef = databaseReference.collection("User").document(uid).collection("Notes").document(noteId)
        var updateNote: HashMap<String, Any> = HashMap<String, Any>()
        updateNote.put("title",newTitle)
        updateNote.put("subTitle",newsubTitle)
        updateNote.put("notes",newNote)
        updateNote.put("id",noteId)
        docRef.set(updateNote).addOnSuccessListener {
            Toast.makeText(requireContext(), "Note Updated", Toast.LENGTH_SHORT).show()
        }
    }

}


