package com.example.fundo.view

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.fundo.databinding.FragmentNotesBinding
import com.example.fundo.model.Notes
import com.example.fundo.viewmodel.NotesViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class NotesFragment : Fragment() {
    private var auth: FirebaseAuth = Firebase.auth
    private var databaseReference: FirebaseFirestore = FirebaseFirestore.getInstance()
    lateinit var notesViewModel: NotesViewModel
    lateinit var binding: FragmentNotesBinding
    lateinit var myDBHelper: MyDBHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNotesBinding.inflate(layoutInflater)
        binding.addNote.setOnClickListener {

            auth = FirebaseAuth.getInstance()
            val title = binding.title.text.toString()
            val subtitle = binding.subTitle.text.toString()
            val note = binding.notes.text.toString()

            var notes = Notes(id = "", title = title, subTitle = subtitle, notes = note)
//           notesViewModel.userNotes(notes)
//            notesViewModel.userNotesStatus.observe(viewLifecycleOwner, Observer {
//                if(it.status){
//
//
//                    Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()
//                    val intent = Intent(requireContext(), HomePage::class.java)
//                    startActivity(intent)
//                }else {
//
//                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
//                }
//            })

            if (!note.equals("")) {

                val uid = auth.currentUser?.uid.toString()
                val docRef = databaseReference.collection("User").document(uid).collection("Notes")
                    .document()
                notes.id = docRef.id
                docRef.get().addOnCompleteListener {
                    if (it.isSuccessful) {
                        val arrayList = ArrayList<Notes>()
                        arrayList.add(notes)
                        insertDataInSqlLite()
                        Toast.makeText(requireContext(), "note saved", Toast.LENGTH_SHORT).show()
                        val intent = Intent(requireContext(), HomePage::class.java)
                        startActivity(intent)
                    }
                }

            } else {

                Toast.makeText(requireContext(), "Please enter note", Toast.LENGTH_SHORT).show()
            }
//
        }
        binding.dismiss.setOnClickListener {
            val intent = Intent(requireContext(), HomePage::class.java)
            startActivity(intent)
        }

        return binding.root


//        return inflater.inflate(R.layout.fragment_notes, container, false)
    }

    private fun insertDataInSqlLite() {

        val title = binding.title.text.toString()
        val subtitle = binding.subTitle.text.toString()
        val note = binding.notes.text.toString()

        val result: Boolean = myDBHelper.sqlInsertData(title,subtitle,note)
        when{
            result-> Toast.makeText(requireContext(),"Data Inserted Successfully...",Toast.LENGTH_LONG).show()
            else -> Toast.makeText(requireContext(),"Failed to insert data...",Toast.LENGTH_LONG).show()
        }
    }


}
