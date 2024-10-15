package com.example.todolistapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes") // чтобы класс Note был таблицей в БД
class Note(

    @PrimaryKey(autoGenerate = true) val id: Int,   // первичный ключ. У новой заметки новый айди

    val text: String,
    val priority: Int,
)