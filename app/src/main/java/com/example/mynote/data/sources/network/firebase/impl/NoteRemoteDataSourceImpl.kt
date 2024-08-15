package com.example.mynote.data.sources.network.firebase.impl

import com.example.mynote.data.models.Note
import com.example.mynote.data.sources.network.firebase.NoteRemoteDataSource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class NoteRemoteDataSourceImpl @Inject constructor(
    firestore: FirebaseFirestore,
    auth: FirebaseAuth
): NoteRemoteDataSource {
    companion object {
        private const val USER_COLLECTION = "users"
        private const val NOTE_COLLECTION = "notes"
    }
    private val userId = auth.currentUser!!.uid
    private val noteCollectionRef = firestore
        .collection(USER_COLLECTION)
        .document(userId)
        .collection(NOTE_COLLECTION)

    override val notes: Flow<List<Note>> = noteCollectionRef.dataObjects()

    override suspend fun addNote(note: Note) {
        val modifiedNote = note.copy(userId = userId)
        noteCollectionRef.add(modifiedNote).await()
    }

    override suspend fun getNoteById(noteId: String): Note? {
        return noteCollectionRef.document(noteId).get().await().toObject()
    }

    override suspend fun update(note: Note) {
        noteCollectionRef.document(note.id).set(note).await()
    }

    override suspend fun delete(noteId: String) {
        noteCollectionRef.document(noteId).delete().await()
    }

    override suspend fun changeIsDone(note: Note) {
        noteCollectionRef.document(note.id).set(note.copy(done = !note.done)).await()
    }
}
