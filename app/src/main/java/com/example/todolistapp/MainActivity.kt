package com.example.todolistapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.todolistapp.databinding.ActivityMainBinding

private var noteDataBase: NoteDataBase? = null

class MainActivity : AppCompatActivity() {

    private lateinit var notesAdapter: NotesAdapter

    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for ActivityLearnWordBinding ust not be null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        noteDataBase = NoteDataBase.getInstance(application)

        binding.buttonAddNote.setOnClickListener {
            val intent: Intent = AddNoteActivity.newIntent(this)
            startActivity(intent)
        }

        notesAdapter = NotesAdapter()
        binding.recyclerViewNotes.adapter = notesAdapter


        val itemTouchHelper = ItemTouchHelper(
            object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
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
                    noteDataBase?.notesDao()?.remove(note.id)
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

    @SuppressLint("NotifyDataSetChanged")
    private fun showNotes() {
        notesAdapter.notes = noteDataBase?.notesDao()?.getNotes() ?: emptyList()
        notesAdapter.notifyDataSetChanged()
    }
}

