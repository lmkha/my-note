package com.example.mynote.data.sources.network.firebase

import com.example.mynote.data.models.Note
import kotlinx.coroutines.flow.Flow

interface NoteRemoteDataSource {
    val notes: Flow<List<Note>>
    suspend fun addNote(note: Note)
    suspend fun getNoteById(noteId: String): Note?
    suspend fun update(note: Note)
    suspend fun delete(noteId: String)
    suspend fun getInfo()
}