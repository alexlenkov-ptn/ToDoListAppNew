package com.example.todolistapp

import android.app.Application
import android.util.Log
import androidx.core.util.Consumer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.Callable

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val noteDataBase = NoteDataBase.getInstance(application)
    private var disposable: Disposable? = null
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var notes: MutableLiveData<List<Note>> = MutableLiveData()


    fun getNotes(): LiveData<List<Note>> {
        return notes
    }

    fun refreshList() {
        disposable = getNotesRx()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
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

        disposable = removeRx(note)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe() {
                Log.d("MainViewModel", "remove" + note.id)
                refreshList()

            }
        disposable?.let { compositeDisposable.add(it) }

    }

    private fun getNotesRx(): Single<List<Note>> {
        return Single.fromCallable { noteDataBase?.notesDao()?.getNotes() ?: emptyList() }
    }

    private fun removeRx(note: Note): Completable {
        return Completable.fromAction {
            noteDataBase?.notesDao()?.remove(note.id)
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}