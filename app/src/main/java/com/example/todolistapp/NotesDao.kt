package com.example.todolistapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NotesDao {

    @Query("SELECT * FROM notes")
    fun getNotes(): List<Note>

    @Insert // помещает объект в таблицу notes
    fun addNote()

    @Query("DELETE FROM NOTES WHERE id = :id") // удаляем по айдишке
    fun remove(id: Int)

}