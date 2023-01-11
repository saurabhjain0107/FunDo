package com.example.fundo.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fundo.R
import com.example.fundo.model.Notes

class RecyclerNoteAdapter(private val noteList: List<Notes>) : RecyclerView.Adapter<RecyclerNoteAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val title:TextView = itemView.findViewById(R.id.titleCard)
        val subTitle:TextView = itemView.findViewById(R.id.SubtitleCard)
        val note:TextView = itemView.findViewById(R.id.noteCard)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
     val view:View = LayoutInflater.from(parent.context).inflate(R.layout.notes_card,parent,false)
       val viewHolder = ViewHolder(view)
        return viewHolder


    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text.get(position)
        holder.subTitle.text.get(position)
        holder.note.text.get(position)

    }

    override fun getItemCount(): Int {
       return noteList.size
    }
}



