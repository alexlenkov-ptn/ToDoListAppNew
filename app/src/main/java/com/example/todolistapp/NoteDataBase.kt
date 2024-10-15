package com.example.todolistapp

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Note::class],
    version = 1
) // При изменении БД номер версии нужно менять
abstract class NoteDataBase : RoomDatabase() {

    private var instance: NoteDataBase? = null
    private val DB_NAME = "notes.db"

    fun getInstance(application: Application): NoteDataBase? {
        if (instance == null) instance = Room.databaseBuilder(
            application,
            NoteDataBase::class.java,
            DB_NAME,
        ).build()
        return instance
    }


}