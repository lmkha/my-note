package com.example.mynote.data.sources.network.firebase.impl

import com.example.mynote.data.models.Note
import com.example.mynote.data.sources.network.firebase.NoteRemoteDataSource
import kotlinx.coroutines.flow.Flow

class NoteRemoteDataSourceImpl: NoteRemoteDataSource {
    companion object {
        private const val NOTE_COLLECTION = "notes"
        private const val USER_ID_FIELD = "userId"
        private const val CREATED_AT_FIELD = "createdAt"
    }

    override val notes: Flow<List<Note>>
        get() = TODO("Not yet implemented")

    override suspend fun addNote(note: Note) {
        TODO("Not yet implemented")
    }

    override suspend fun getNoteById(noteId: String): Note? {
        TODO("Not yet implemented")
    }

    override suspend fun save(note: Note): String {
        TODO("Not yet implemented")
    }

    override suspend fun update(note: Note) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(noteId: String) {
        TODO("Not yet implemented")
    }
}