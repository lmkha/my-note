package com.example.mynote.composes.home

import com.example.mynote.data.models.Note

data class HomeUiState(
    val notes: List<Note> = emptyList(),
    val userEmail: String = "",
    val isSignOut: Boolean = false,
    val isSignOutSuccess: Boolean = false,
    val resultMessage: String = ""
)
