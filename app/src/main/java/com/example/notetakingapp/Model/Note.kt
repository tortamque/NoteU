package com.example.notetakingapp.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
class Note(
    val title: String,
    val description: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}