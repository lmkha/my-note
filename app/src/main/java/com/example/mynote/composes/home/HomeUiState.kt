package com.example.mynote.composes.home

data class HomeUiState(
    val userEmail: String = "",
    val isSignOut: Boolean = false,
    val isSignOutSuccess: Boolean = false,
    val resultMessage: String = ""
)
