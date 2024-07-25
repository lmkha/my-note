package com.example.mynote.composes.signup

data class SignUpUiState(
    val email: String = "",
    val password: String = "",
    val isSignUp: Boolean = false,
    val isSignUpSuccess: Boolean = false,
    val resultMessage: String = ""
)