package com.example.todolistapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val noteDataBase = NoteDataBase.getInstance(application)

    fun getNotes(): LiveData<List<Note>>? {
        return noteDataBase?.notesDao()?.getNotes()
    }

    fun remove(note: Note) {
        val thread = Thread {
            noteDataBase?.notesDao()?.remove(note.id)
        }
        thread.start()
    }

}