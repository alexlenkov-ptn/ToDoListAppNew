package com.example.todolistapp

import android.app.Application
import android.util.Log
import androidx.core.util.Consumer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val noteDataBase = NoteDataBase.getInstance(application)
    private var disposable: Disposable? = null
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var notes: MutableLiveData<List<Note>> = MutableLiveData()

    fun getNotes(): LiveData<List<Note>> {
        return notes
    }

    fun refreshList() {
        disposable = noteDataBase?.notesDao()?.getNotes()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                { notesFromDb ->
                    notes.value = notesFromDb
                },
                { error ->
                    Log.d("MainViewModel", "refreshList error: $error")
                }
            )
        disposable?.let { compositeDisposable.add(it) }
    }

    fun remove(note: Note) {

        disposable = noteDataBase?.notesDao()?.remove(note.id)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe() {
                Log.d("MainViewModel", "remove" + note.id)
                refreshList()

            }

        disposable?.let { compositeDisposable.add(it) }

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}