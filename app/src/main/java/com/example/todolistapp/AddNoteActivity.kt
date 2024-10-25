package com.example.todolistapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.todolistapp.databinding.ActivityAddNoteBinding
import java.lang.IllegalStateException


class AddNoteActivity : AppCompatActivity() {
    private var _binding: ActivityAddNoteBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for ActivityAddNoteBinding ust not be null")

    private lateinit var viewModel: AddNoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[AddNoteViewModel::class.java]

        binding.buttonSave.setOnClickListener {
            saveNote()
        }

        viewModel.shouldCloseScreen.observe(
            this
        ) { shouldClose -> if (shouldClose) finish() }
    }

    private fun saveNote() {
        val text = binding.editTextTextNewNote.text.toString().trim()
        val priority = getPriority()
        val note = Note(text = text, priority = priority)
        viewModel.saveNote(note)
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


