package com.example.todolistapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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

