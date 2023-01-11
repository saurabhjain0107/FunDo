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

//    val noteList = ArrayList<Notes>()
    lateinit var binding : ActivityHomePageBinding

    lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
        supportActionBar?.hide()

        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerview = findViewById<RecyclerView>(R.id.recyclerNote)

        recyclerview.layoutManager = LinearLayoutManager(this)

        val note = Notes()

        var noteList : HashMap<String, String> = HashMap<String, String> ()
        noteList.put("title",note.title)
        noteList.put("subTitle",note.subTitle)
        noteList.put("note",note.notes)

        val adapter = RecyclerNoteAdapter(noteList)
        recyclerview.adapter = adapter


        val dialog = Dialog(this)
        dialog.setContentView(R.layout.fragment_profile)

        binding.profileImage.setOnClickListener {

//            val profile = ProfileFragment()
//            supportFragmentManager.beginTransaction().replace(R.id.drawerLayout,profile).commit()

            dialog.show()



        }


        binding.floatingBtn.setOnClickListener {

            val note = NotesFragment()
            supportFragmentManager.beginTransaction().replace(R.id.drawerLayout,note).commit()

        }



//        val drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
//        val navView : NavigationView = findViewById(R.id.nav_view)



//        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
//
//        drawerLayout.addDrawerListener(toggle)
//        toggle.syncState()
//
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//
//        navView.setNavigationItemSelectedListener {
//            when(it.itemId){
//                R.id.side_note -> Toast.makeText(applicationContext,"Clicked Note",Toast.LENGTH_SHORT).show()
//                R.id.Reminders -> Toast.makeText(applicationContext,"Clicked Reminders",Toast.LENGTH_SHORT).show()
//                R.id.delete -> Toast.makeText(applicationContext,"Clicked Delete",Toast.LENGTH_SHORT).show()
//            }
//            true
//        }

    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//
//        if(toggle.onOptionsItemSelected(item)){
//            return true
//        }
//
//        return super.onOptionsItemSelected(item)
//    }
}