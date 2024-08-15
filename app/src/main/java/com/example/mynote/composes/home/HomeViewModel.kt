package com.example.mynote.composes.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynote.data.models.Note
import com.example.mynote.data.repositories.AccountRepository
import com.example.mynote.data.repositories.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val noteRepository: NoteRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        collectCurrentUser()
        collectNotes()
    }

    private fun collectCurrentUser() {
        viewModelScope.launch {
            accountRepository.currentUser.collect { currentUser ->
                _uiState.update {
                    it.copy(
                        userName = currentUser?.name.orEmpty(),
                        isSignOut = currentUser == null
                    )
                }
            }
        }
    }

    private fun collectNotes() {
        viewModelScope.launch {
            try {
                noteRepository.notes.collect { newNote ->
                    _uiState.update {
                        it.copy(notes = newNote)
                    }
                }
            } catch (throwable: Throwable) {
                Log.e("CHECK_VAR", "HomeViewModel inside collect fun error: ${throwable.message}")
            }
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteRepository.deleteNote(note)
        }
    }

    fun changeIsDone(note: Note) {
        viewModelScope.launch {
            noteRepository.changeIsDone(note)
        }
    }

    fun signOut() {
        viewModelScope.launch {
            accountRepository.signOut()
        }
    }
}
