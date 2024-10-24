package com.example.todolistapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.todolistapp.databinding.ActivityMainBinding

private var noteDataBase: NoteDataBase? = null

class MainActivity : AppCompatActivity() {

//    private val handler = Handler(Looper.getMainLooper()) // сюда отправим объект Runnable, который будет вызывать метод run в главном потоке
// ЗАМЕНЕН Live Data

    private lateinit var notesAdapter: NotesAdapter

    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for ActivityLearnWordBinding ust not be null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        noteDataBase = NoteDataBase.getInstance(application)

        binding.buttonAddNote.setOnClickListener {
            val intent: Intent = AddNoteActivity.newIntent(this)
            startActivity(intent)
        }

        notesAdapter = NotesAdapter()
        binding.recyclerViewNotes.adapter = notesAdapter

        noteDataBase?.notesDao()?.getNotes()?.observe(this, object : Observer<List<Note>> {
            override fun onChanged(notes: List<Note>) {
                notesAdapter.notes = notes
//                notesAdapter.notifyDataSetChanged()
            }

        })

        val itemTouchHelper = ItemTouchHelper(
            object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    val note = notesAdapter.notes[position]

                    val thread = Thread { // Создан поток. Внутри него удалим элемент
                        noteDataBase?.notesDao()?.remove(note.id)
//                        handler.post(kotlinx.coroutines.Runnable { // сюда прилетает объект run, который вызывается на главном потоке
//                            showNotes() // вызываем на главном потоке
//                        }) ЗАМЕНЕН Live Data

                    }
                    thread.start()
                }
            }
        )

        itemTouchHelper.attachToRecyclerView(binding.recyclerViewNotes)

        notesAdapter.onNoteClickListener = object : NotesAdapter.OnNoteClickListener {
            override fun onNoteClick(note: Note) {
            }
        }

    }

//    override fun onResume() {
//        super.onResume()
//        showNotes()
//    }
    // После добавления LiveData не нужно делать

//    @SuppressLint("NotifyDataSetChanged")
//    private fun showNotes() {
//        val thread = Thread {
//            val notes : List<Note> = noteDataBase?.notesDao()?.getNotes() ?: emptyList() // получение данных в фоновом потоке
//
////            notesAdapter.notes = noteDataBase?.notesDao()?.getNotes() ?: emptyList()
//
//            handler.post(kotlinx.coroutines.Runnable {
//                notesAdapter.notes = notes
//                notesAdapter.notifyDataSetChanged()
//            })
//        }
//        thread.start()
//    }
    // ЗАМЕНЕН на LiveData

}

