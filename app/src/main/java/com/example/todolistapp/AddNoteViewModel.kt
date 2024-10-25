package com.example.todolistapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class AddNoteViewModel(application: Application) : AndroidViewModel(application) {

    private val notesDao: NotesDao = NoteDataBase.getInstance(application)?.notesDao()
        ?: throw IllegalArgumentException("NotesDao is not available")

    var shouldCloseScreen: MutableLiveData<Boolean> = MutableLiveData(false)

    fun saveNote(note: Note) {
        val thread = Thread {
            notesDao.addNote(note)
            shouldCloseScreen.postValue(true)
        }
        thread.start()
    }

}