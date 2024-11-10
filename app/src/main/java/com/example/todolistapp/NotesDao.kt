package com.example.todolistapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface NotesDao {

    @Query("SELECT * FROM notes")
    fun getNotes() : List<Note>


    @Insert(onConflict = OnConflictStrategy.REPLACE) // помещает объект в таблицу notes
    fun addNote(note: Note)


    @Query("DELETE FROM NOTES WHERE id = :id") // удаляем по айдишке
    fun remove(id: Int)

}