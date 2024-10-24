package com.example.todolistapp

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Note::class],
    version = 1,
    exportSchema = false,
) // При изменении БД номер версии нужно менять
abstract class NoteDataBase : RoomDatabase() {

    companion object {
        private var instance: NoteDataBase? = null

        fun getInstance(application: Application): NoteDataBase? {
            if (instance == null) instance = Room.databaseBuilder(
                application,
                NoteDataBase::class.java,
                Constants.DB_NAME,
            )
//                .allowMainThreadQueries() // позволяет обойти ограничение, чтобы обойти главный поток
                // можно указывать только в целях теста
                .build()
            return instance
        }
    }

    abstract fun notesDao() : NotesDao

}