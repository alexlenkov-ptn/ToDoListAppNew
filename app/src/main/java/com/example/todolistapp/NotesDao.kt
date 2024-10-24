package com.example.todolistapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NotesDao {

    @Query("SELECT * FROM notes")
    fun getNotes(): LiveData<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE) // помещает объект в таблицу notes
    fun addNote(note: Note)

    @Query("DELETE FROM NOTES WHERE id = :id") // удаляем по айдишке
    fun remove(id: Int)

}