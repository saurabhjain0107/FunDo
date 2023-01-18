package com.example.fundo.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fundo.R
import com.example.fundo.model.Notes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class RecyclerNoteAdapter(val noteList: List<Notes>) : RecyclerView.Adapter<RecyclerNoteAdapter.ViewHolder>() {
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
        val current = noteList[position]
        holder.title.text= current.title
        holder.subTitle.text = current.subTitle
        holder.note.text= current.notes
        holder.optionMenu.setOnClickListener {
            val popupMenu: PopupMenu = PopupMenu(it.context,holder.optionMenu)
            popupMenu.inflate(R.menu.card_menu)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when(item.itemId){
                    R.id.menu_delete ->

                      deleteNote(current.id)
                }
                true
            })
            popupMenu.show()
        }

    }

    private fun deleteNote(id : String) {
        val uid = auth.currentUser?.uid.toString()
        val docRef = databaseReference.collection("User").document(uid).collection("Notes").document(id)
        docRef.delete()
    }

    override fun getItemCount(): Int {
       return noteList.size
    }
}




