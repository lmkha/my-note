package com.example.mynote.composes.login

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = true,
    val isLoginSuccess: Boolean = false,
    val error: String = ""
)
