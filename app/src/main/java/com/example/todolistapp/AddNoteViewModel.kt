package com.example.todolistapp

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.functions.Action
import io.reactivex.rxjava3.schedulers.Schedulers

class AddNoteViewModel(application: Application) : AndroidViewModel(application) {

    private val notesDao: NotesDao = NoteDataBase.getInstance(application)?.notesDao()
        ?: throw IllegalArgumentException("NotesDao is not available")

    var shouldCloseScreen: MutableLiveData<Boolean> = MutableLiveData(false)

    fun saveNote(note: Note) {

        notesDao.addNote(note)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                shouldCloseScreen.postValue(true)
            }

    }

}