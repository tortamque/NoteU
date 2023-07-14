package com.example.notetakingapp.View

import android.app.AlertDialog
import android.app.Dialog
import android.content.ClipData.Item
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notetakingapp.Adapters.NoteAdapter
import com.example.notetakingapp.Model.Note
import com.example.notetakingapp.NoteApplication
import com.example.notetakingapp.R
import com.example.notetakingapp.ViewModel.NoteViewModel
import com.example.notetakingapp.ViewModel.NoteViewModelFactory

class MainActivity : AppCompatActivity() {
    lateinit var noteViewModel: NoteViewModel
    lateinit var addActivityResultLauncher: ActivityResultLauncher<Intent>
    lateinit var updateActivityResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.RecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val noteAdapter = NoteAdapter(this)
        recyclerView.adapter = noteAdapter

        registerActivityResultLauncher()

        val viewModelFactory = NoteViewModelFactory((application as NoteApplication).repository)

        noteViewModel = ViewModelProvider(this, viewModelFactory).get(NoteViewModel::class.java)
        noteViewModel.allNotes.observe(this, Observer { notes ->
            noteAdapter.setNote(notes)
        })

        ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                noteViewModel.delete(noteAdapter.getNote(viewHolder.adapterPosition))
            }

        }).attachToRecyclerView(recyclerView)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.new_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.add_note_item -> {
                val intent = Intent(this, NoteAddActivity::class.java)
                addActivityResultLauncher.launch(intent)
            }
            R.id.delete_all_item -> showDialogMessage()
        }

        return true
    }

    fun showDialogMessage(){
        val dialogMessage = AlertDialog.Builder(this)
            .setTitle("Delete all notes")
            .setMessage("Press \"Yes\" if you want to delete all notes. Otherwise press \"No\".")
            .setNegativeButton("No", DialogInterface.OnClickListener{ dialog, which ->
                dialog.cancel()
            })
            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
                noteViewModel.deleteAllNotes()
            })
            .create().show()
    }

    fun registerActivityResultLauncher(){
        addActivityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback { resultAddNote ->
                val resultCode = resultAddNote.resultCode
                val data = resultAddNote.data

                if(resultCode == RESULT_OK && data != null){
                    val title: String = data.getStringExtra("title").toString()
                    val description: String = data.getStringExtra("description").toString()

                    val note = Note(title, description)
                    noteViewModel.insert(note)
                }
            }
        )

        updateActivityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback { resultUpdateNote ->
                val resultCode = resultUpdateNote.resultCode
                val data = resultUpdateNote.data

                if(resultCode == RESULT_OK && data != null){
                    val updatedTitle: String = data.getStringExtra("updatedTitle").toString()
                    val updatedDescription: String = data.getStringExtra("updatedDescription").toString()
                    val noteID = data.getIntExtra("noteID", -1)

                    val newNote = Note(updatedTitle, updatedDescription)
                    newNote.id = noteID

                    noteViewModel.update(newNote)
                }
            }
        )
    }
}