package com.example.todolistapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MainViewModel(application: Application) : AndroidViewModel(application) {
    var count = 0
    private val countLD = MutableLiveData<Int>()
    private val noteDataBase = NoteDataBase.getInstance(application)

    fun getNotes() : LiveData<List<Note>>? {
        return noteDataBase?.notesDao()?.getNotes()
    }

    fun showCount() {
        count++
        countLD.value = count
    }

    fun getCount() : LiveData<Int> {
        return countLD
    }

    fun remove(note: Note) {
        val thread = Thread(Runnable {
            noteDataBase?.notesDao()?.remove(note.id)
        })
        thread.start()
    }

}