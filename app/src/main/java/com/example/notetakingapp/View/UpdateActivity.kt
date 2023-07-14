package com.example.notetakingapp.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.notetakingapp.R

class UpdateActivity : AppCompatActivity() {
    lateinit var titleEditText: EditText
    lateinit var descriptionEditText: EditText
    lateinit var cancelButton: Button
    lateinit var saveButton: Button
    var currentID: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        supportActionBar?.title = "Update Note"

        titleEditText = findViewById(R.id.noteTitleEditTextUpdate)
        descriptionEditText = findViewById(R.id.noteDescriptionEditTextUpdate)
        cancelButton = findViewById(R.id.cancelButtonUpdate)
        saveButton = findViewById(R.id.saveButtonUpdate)

        initData()

        cancelButton.setOnClickListener {
            Toast.makeText(applicationContext, "Nothing updated", Toast.LENGTH_SHORT).show()
            finish()
        }

        saveButton.setOnClickListener {
            updateNote()
        }
    }

    fun updateNote(){
        val updatedTitle = titleEditText.text.toString()
        val updatedDescription = descriptionEditText.text.toString()

        val intent = Intent()
            .putExtra("updatedTitle", updatedTitle)
            .putExtra("updatedDescription", updatedDescription)

        if(currentID != -1){
            intent.putExtra("noteID", currentID)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    fun initData(){
        val currentTitle = intent.getStringExtra("title")
        val currentDescription = intent.getStringExtra("description")
        currentID = intent.getIntExtra("id", -1)

        titleEditText.setText(currentTitle)
        descriptionEditText.setText(currentDescription)
    }
}