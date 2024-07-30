package com.example.mynote.data.repositories.impl

import android.util.Log
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

    override suspend fun getInfo() {
        Log.i("CHECK_VAR", "Repository, Notes: $notes")
        noteRemoteDataSource.getInfo()
    }
}
