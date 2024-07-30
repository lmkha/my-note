package com.example.mynote.data.repositories

import com.example.mynote.data.models.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    val notes: Flow<List<Note>>
    suspend fun addNewNote(note: Note)
    suspend fun getInfo()
}
