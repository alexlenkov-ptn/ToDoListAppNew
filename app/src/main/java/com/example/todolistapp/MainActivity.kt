package com.example.todolistapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.todolistapp.databinding.ActivityMainBinding

private lateinit var viewModel: MainViewModel

class MainActivity : AppCompatActivity() {


    private val notesAdapter = NotesAdapter()

    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for ActivityLearnWordBinding ust not be null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewModelProvider(this@MainActivity)[MainViewModel::class.java].also { viewModel = it }

        binding.buttonAddNote.setOnClickListener {
            val intent: Intent = AddNoteActivity.newIntent(this)
            startActivity(intent)
        }

        binding.recyclerViewNotes.adapter = notesAdapter

        viewModel.getNotes()?.observe(this, object : Observer<List<Note>> {
            override fun onChanged(notes: List<Note>) {
                notesAdapter.updateNotes(notes)
            }

        })

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
                    viewModel.remove(note)

                }
            }
        )

        itemTouchHelper.attachToRecyclerView(binding.recyclerViewNotes)

    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshList()
    }


}

