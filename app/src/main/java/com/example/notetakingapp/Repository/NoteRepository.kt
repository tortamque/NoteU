package com.example.notetakingapp.Repository

import androidx.annotation.WorkerThread
import com.example.notetakingapp.Model.Note
import com.example.notetakingapp.Room.NoteDAO
import kotlinx.coroutines.flow.Flow

class NoteRepository(
    private val noteDAO: NoteDAO
) {
    val allNotes: Flow<List<Note>> = noteDAO.getAllNotes()

    @WorkerThread
    suspend fun insert(note: Note){
        noteDAO.insert(note)
    }

    @WorkerThread
    suspend fun update(note: Note){
        noteDAO.update(note)
    }

    @WorkerThread
    suspend fun delete(note: Note){
        noteDAO.delete(note)
    }

    @WorkerThread
    suspend fun deleteAllNotes(){
        noteDAO.deleteAllNotes()
    }
}