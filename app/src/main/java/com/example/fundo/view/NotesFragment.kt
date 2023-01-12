package com.example.fundo.view

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.fundo.R
import com.example.fundo.databinding.FragmentNotesBinding
import com.example.fundo.model.Notes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class NotesFragment : Fragment() {
    private var auth: FirebaseAuth = Firebase.auth
    private var databaseReference: FirebaseFirestore = FirebaseFirestore.getInstance()

    lateinit var binding: FragmentNotesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotesBinding.inflate(inflater)

        binding.addNote.setOnClickListener {

            auth = FirebaseAuth.getInstance()
            val title = binding.title.text.toString()
            val subtitle = binding.subTitle.text.toString()
            val note = binding.notes.text.toString()

            var notes = Notes(id = "", title = title, subTitle = subtitle, notes = note)

            if (!note.equals("")) {

                val uid = auth.currentUser?.uid.toString()
                val docRef = databaseReference.collection("User").document(uid).collection("Notes")
                    .document()
                notes.id = docRef.id
                docRef.get().addOnCompleteListener {
                    if (it.isSuccessful) {


                        val arrayList = ArrayList<Notes>()
                        arrayList.add(notes)

                        Toast.makeText(requireContext(), "note saved", Toast.LENGTH_SHORT).show()
                        val intent = Intent(requireContext(), HomePage::class.java)
                        startActivity(intent)
                    }
                }

            } else {

                Toast.makeText(requireContext(), "Please enter note", Toast.LENGTH_SHORT).show()
            }

        }
        binding.dismiss.setOnClickListener {
            val intent = Intent(requireContext(), HomePage::class.java)
            startActivity(intent)
        }

        return binding.root

//        return inflater.inflate(R.layout.fragment_notes, container, false)
    }


}
