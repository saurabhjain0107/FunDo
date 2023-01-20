package com.example.fundo.view

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fundo.R
import com.example.fundo.databinding.ActivityHomePageBinding
import com.example.fundo.model.Notes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class HomePage : AppCompatActivity() {
    private var auth: FirebaseAuth = Firebase.auth
    private var databaseReference: FirebaseFirestore = FirebaseFirestore.getInstance()
    lateinit var binding : ActivityHomePageBinding
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    var noteList = arrayListOf<Notes>()
    lateinit var recyclerview : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
            WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM
        )
        supportActionBar?.hide()

        noteData()
       binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpView()
        binding.floatingBtn.setOnClickListener {
//            val dialog = Dialog(this)
//            dialog.setContentView(R.layout.fragment_dialog_profile)
//            dialog.show()
            val note = NotesFragment()
            supportFragmentManager.beginTransaction().replace(R.id.drawerLayout, note).commit()
        }

        recyclerview = findViewById<RecyclerView>(R.id.recyclerNote)

        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.setHasFixedSize(true)
    }
    private fun setUpView() {
        setUpDrawerLayout()
    }

    private fun setUpDrawerLayout() {
        val toolbar: Toolbar? = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        actionBarDrawerToggle = ActionBarDrawerToggle(this,binding.drawerLayout,R.string.app_name,R.string.app_name)
        actionBarDrawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_drawer_menu,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){

            when(item.itemId){
                R.id.profilePic ->{
                    val dialog = Dialog(this)
                    dialog.setContentView(R.layout.fragment_dialog_profile)
                    dialog.show()
                }
                R.id.search -> {
                 fragmentChange()
                }
//                R.id.delete ->{
//                    val dialog = Dialog(this)
//                    dialog.setContentView(R.layout.fragment_dialog_profile)
//                    dialog.show()
//                    true
//                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun noteData() {
        var notes = Notes()

        val tempArrayList = arrayListOf<Notes>()
        val uid = auth.currentUser?.uid.toString()
        val docRef = databaseReference.collection("User").document(uid).collection("Notes")
        docRef.get().addOnCompleteListener {
            if(it.isSuccessful){
                for (document in it.result){
                    notes = Notes(
                        document["id"].toString(),
                        document["title"].toString(),
                        document["subTitle"].toString(),
                        document["notes"].toString(),
                    )
                    noteList.add(notes)
                    val adapter = RecyclerNoteAdapter(noteList)
                    recyclerview.adapter = adapter
                }
            }
        }
    }
    fun fragmentChange(){
        val note = NotesFragment()
        supportFragmentManager.beginTransaction().replace(R.id.drawerLayout, note).commit()
    }
}


