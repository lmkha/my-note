package com.example.mynote.composes.add_edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynote.data.models.Note
import com.example.mynote.data.repositories.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteRepository: NoteRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(AddEditUiState())
    val uiState = _uiState.asStateFlow()

    fun addNewNote(title: String, content: String) {
        viewModelScope.launch {
            noteRepository.addNewNote(
                Note(
                    title = title,
                    content = content
                )
            )
        }
        _uiState.update {
            it.copy(isAddEditNoteSuccess = true)
        }
    }
}
