package com.example.notetakingapp.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.notetakingapp.R

class NoteAddActivity : AppCompatActivity() {
    lateinit var titleEditText: EditText
    lateinit var descriptionEditText: EditText
    lateinit var cancelButton: Button
    lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_add)
        supportActionBar?.title = "Add Note"

        titleEditText = findViewById(R.id.noteTitleEditText)
        descriptionEditText = findViewById(R.id.noteDescriptionEditText)
        cancelButton = findViewById(R.id.cancelButton)
        saveButton = findViewById(R.id.saveButton)

        cancelButton.setOnClickListener {
            Toast.makeText(applicationContext, "Nothing saved", Toast.LENGTH_SHORT).show()
            finish()
        }

        saveButton.setOnClickListener {
            saveNote()
        }
    }

    fun saveNote(){
        val title: String = titleEditText.text.toString()
        val description: String = descriptionEditText.text.toString()

        if(title.isNotEmpty() && description.isNotEmpty()){
            val intent = Intent()
            intent.putExtra("title", title)
            intent.putExtra("description", description)
            setResult(RESULT_OK, intent)
            finish()
        } else if(title.isEmpty()){
            Toast.makeText(applicationContext, "The title is empty", Toast.LENGTH_SHORT).show()
        } else{
            Toast.makeText(applicationContext, "The description is empty", Toast.LENGTH_SHORT).show()
        }
    }
}