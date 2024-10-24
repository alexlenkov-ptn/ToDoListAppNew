package com.example.todolistapp

import kotlin.random.Random

class Database private constructor() {

    private val notesMutableList: MutableList<Note> = mutableListOf()

    companion object {
        private var instance: Database? = null

        fun getInstance(): Database {
            if (instance == null) {
                instance = Database()
            }
            return instance!!
        }
    }

//    init {
//        for (i in 0..20) {
//            val note = Note(0, "Note #$i", Random.nextInt(3))
//            notesMutableList.add(note)
//        }
//    }

    fun add(note: Note) {
        notesMutableList.add(note)
    }

    fun remove(id: Int) {
        val noteToRemove = notesMutableList.find { it.id == id }

        if (noteToRemove != null) {
            notesMutableList.remove(noteToRemove)
        }
    }

    fun getNotes(): List<Note> {
        return notesMutableList.toList()
    }

}