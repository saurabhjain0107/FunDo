package com.example.fundo.view

import android.app.FragmentManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

import androidx.recyclerview.widget.RecyclerView
import com.example.fundo.R

import com.example.fundo.model.Notes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


class RecyclerNoteAdapter(val noteList: List<Notes>,val context: Context) : RecyclerView.Adapter<RecyclerNoteAdapter.ViewHolder>() {

    private var auth: FirebaseAuth = Firebase.auth
    private var databaseReference: FirebaseFirestore = FirebaseFirestore.getInstance()
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title:TextView = itemView.findViewById(R.id.titleCard)
        val subTitle:TextView = itemView.findViewById(R.id.SubtitleCard)
        val note:TextView = itemView.findViewById(R.id.noteCard)
        val  optionMenu: TextView = itemView.findViewById(R.id.txtOptionMenu)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.notes_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text= noteList[position].title
        holder.subTitle.text =noteList[position].subTitle
        holder.note.text= noteList[position].notes

        holder.optionMenu.setOnClickListener {
            val popupMenu: PopupMenu = PopupMenu(it.context,holder.optionMenu)
            popupMenu.menuInflater.inflate(R.menu.card_menu,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when(item.itemId){

                    R.id.menu_edit ->{

                        val bundle = Bundle()
                        bundle.putString("noteId",noteList[position].id)
                        bundle.putString("title",noteList[position].title)
                        bundle.putString("subTitle",noteList[position].subTitle)
                        bundle.putString("notes",noteList[position].notes)
                        val editFrag = EditNoteFragment()
                        editFrag.arguments = bundle
                        val transaction =
                            (context as AppCompatActivity).supportFragmentManager.beginTransaction()
                        transaction.replace(R.id.home_container, editFrag)
                        transaction.addToBackStack(null)
                        transaction.commit()
                    }
                    R.id.menu_delete ->{
                                deleteNote(noteList[position].id)
                        notifyDataSetChanged()
                    }
                }
                true
            })
            popupMenu.show()
        }

    }
    private fun deleteNote(id : String) {
        val uid = auth.currentUser?.uid.toString()
        databaseReference.collection("User").document(uid)
            .collection("Notes").document(id).delete().addOnCompleteListener{
        if(it.isSuccessful){
            Log.d("Delete note","$uid, $id")
        }else {
            Log.d("Delete note failed","$uid, $id")
        }
       }
    }
    override fun getItemCount(): Int {
       return noteList.size
    }
}


