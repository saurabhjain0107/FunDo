package com.example.fundo.view

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
            WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM
        )
        supportActionBar?.hide()
        val homeFrag = HomeFragment()
        supportFragmentManager.beginTransaction().add(R.id.home_container,homeFrag).commit()

       binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpView()
//        binding.floatingBtn.setOnClickListener {
////            val dialog = Dialog(this)
////            dialog.setContentView(R.layout.fragment_dialog_profile)
////            dialog.show()
//            val note = NotesFragment()
//            supportFragmentManager.beginTransaction().replace(R.id.drawerLayout, note).commit()
//        }

//        recyclerview = findViewById<RecyclerView>(R.id.recyclerNote)
//
//        recyclerview.layoutManager = LinearLayoutManager(this)
//        recyclerview.setHasFixedSize(true)
    }
    private fun setUpView() {
        setUpDrawerLayout()
    }

    private fun setUpDrawerLayout() {
        val toolbar: Toolbar? = findViewById(R.id.toolbar)
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

            var profile :ImageView = findViewById(R.id.profilePic)
            profile.setOnClickListener { 

            }
            when(item.itemId) {
//
//                    val dialog = Dialog(this)
//                    dialog.setContentView(R.layout.fragment_dialog_profile)
//                    dialog.show()
//
//


//                R.id.profilePic ->
//
//                {

                }
                        
//                R.id.delete ->{
//                    val dialog = Dialog(this)
//                    dialog.setContentView(R.layout.fragment_dialog_profile)
//                    dialog.show()
//                    true
//                }
            }
//        }
        return super.onOptionsItemSelected(item)
    }

    fun fragmentChange(){
        val note = NotesFragment()
        supportFragmentManager.beginTransaction().replace(R.id.drawerLayout, note).commit()
    }
}


