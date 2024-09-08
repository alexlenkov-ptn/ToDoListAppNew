package com.example.todolistapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter() :
    RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    var notes: List<Note> = listOf()

    fun updateNotes(newNotes: List<Note>) {
        notes = newNotes
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.note_item,
            parent,
            false
        )
        return NotesViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: NotesViewHolder, position: Int) {
        val note = notes[position]

        viewHolder.textViewNote.text = note.text

        val colorResId: Int = when (note.priority) {
            0 -> android.R.color.holo_green_dark
            1 -> android.R.color.holo_orange_dark
            else -> android.R.color.holo_red_dark
        }
        val color: Int = ContextCompat.getColor(viewHolder.itemView.context, colorResId)
        viewHolder.textViewNote.setBackgroundColor(color)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewNote: TextView = itemView.findViewById(R.id.textViewNote)
    }

}