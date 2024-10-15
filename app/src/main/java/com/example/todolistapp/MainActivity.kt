package com.example.todolistapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.todolistapp.databinding.ActivityMainBinding

private val database : Database = Database.getInstance()

class MainActivity : AppCompatActivity() {

    private lateinit var notesAdapter : NotesAdapter

    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for ActivityLearnWordBinding ust not be null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonAddNote.setOnClickListener {
            val intent: Intent = AddNoteActivity.newIntent(this)
            startActivity(intent)
        }

        notesAdapter = NotesAdapter()
        binding.recyclerViewNotes.adapter = notesAdapter

        val itemTouchHelper = ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or  ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    val note = notesAdapter.notes[position]
                    database.remove(note.id)
                    showNotes()
                }
            }
        )

        itemTouchHelper.attachToRecyclerView(binding.recyclerViewNotes)

        notesAdapter.onNoteClickListener = object : NotesAdapter.OnNoteClickListener {
            override fun onNoteClick(note: Note) {
            }
        }
    }

    override fun onResume() {
        super.onResume()
        showNotes()
    }

    private fun showNotes() {
        notesAdapter.notes = database.getNotes()
        notesAdapter.updateNotes(database.getNotes())
    }
}

