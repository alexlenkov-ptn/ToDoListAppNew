package com.example.todolistapp

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Action
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class AddNoteViewModel(application: Application) : AndroidViewModel(application) {

    private val notesDao: NotesDao = NoteDataBase.getInstance(application)?.notesDao()
        ?: throw IllegalArgumentException("NotesDao is not available")

    var shouldCloseScreen: MutableLiveData<Boolean> = MutableLiveData(false)

    private val compositeDisposable : CompositeDisposable = CompositeDisposable()

    private lateinit var disposable : Disposable

    fun saveNote(note: Note) {

        disposable = notesDao.addNote(note)
            .delay(5, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d("AddNoteViewModel", "subscribe")
                shouldCloseScreen.postValue(true)
            }
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}