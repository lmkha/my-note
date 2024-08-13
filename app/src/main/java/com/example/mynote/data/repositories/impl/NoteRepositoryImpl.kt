package com.example.mynote.data.repositories.impl

import com.example.mynote.data.models.Note
import com.example.mynote.data.repositories.NoteRepository
import com.example.mynote.data.sources.network.firebase.NoteRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteRemoteDataSource: NoteRemoteDataSource
): NoteRepository {
    override val notes: Flow<List<Note>> = noteRemoteDataSource.notes

    override suspend fun addNewNote(note: Note) {
        noteRemoteDataSource.addNote(note)
    }

    override suspend fun updateNote(note: Note) {
       noteRemoteDataSource.update(note)
    }

    override suspend fun getNoteById(noteId: String): Note? {
        return noteRemoteDataSource.getNoteById(noteId)
    }
}
