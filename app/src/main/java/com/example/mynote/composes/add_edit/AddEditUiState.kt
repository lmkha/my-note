package com.example.mynote.composes.add_edit

data class AddEditUiState(
    val title: String = "",
    val content: String = "",
    val isAddEditNoteSuccess: Boolean = false,
)
