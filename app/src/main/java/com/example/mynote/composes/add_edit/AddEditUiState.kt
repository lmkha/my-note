package com.example.mynote.composes.add_edit

import com.example.mynote.data.models.Note

data class AddEditUiState(
    val sentNote: Note? = null,
    val title: String = "",
    val content: String = "",
    val isAddEditNoteSuccess: Boolean = false,
)
