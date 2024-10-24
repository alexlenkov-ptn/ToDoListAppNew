package com.example.todolistapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todolistapp.databinding.ActivityAddNoteBinding
import java.lang.IllegalStateException


class AddNoteActivity : AppCompatActivity() {
    private var _binding: ActivityAddNoteBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for ActivityAddNoteBinding ust not be null")

    private var noteDataBase: NoteDataBase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        noteDataBase = NoteDataBase.getInstance(application)

        binding.buttonSave.setOnClickListener {
            saveNote()
        }
    }

    private fun saveNote() {
        val text = binding.editTextTextNewNote.text.toString().trim()
        val priority = getPriority()
        val note = Note(text = text, priority = priority)
        noteDataBase?.notesDao()?.addNote(note)
        finish()
    }

    private fun getPriority(): Int {
        return when {
            binding.radioButtonLow.isChecked -> 0
            binding.radioButtonMedium.isChecked -> 1
            else -> 2
        }
    }


    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, AddNoteActivity::class.java)
        }
    }

}


