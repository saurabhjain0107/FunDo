package com.example.fundo.view

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fundo.R
import com.example.fundo.databinding.ActivityHomePageBinding
import com.example.fundo.model.Notes

class HomePage : AppCompatActivity() {
    lateinit var binding : ActivityHomePageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
            WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM
        )
        supportActionBar?.hide()

        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val notesObjects : MutableList<Notes> = ArrayList()
        notesObjects.add(Notes(title = Notes().title, subTitle = Notes().subTitle, notes = Notes().notes))
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerNote)

        recyclerview.layoutManager = LinearLayoutManager(this)

        val adapter = RecyclerNoteAdapter(notesObjects)
        recyclerview.adapter = adapter

        val dialog = Dialog(this)
        dialog.setContentView(R.layout.fragment_profile)

        binding.profileImage.setOnClickListener {
            dialog.show()
        }

        binding.floatingBtn.setOnClickListener {

            val note = NotesFragment()
            supportFragmentManager.beginTransaction().replace(R.id.drawerLayout, note).commit()

        }
    }
}

