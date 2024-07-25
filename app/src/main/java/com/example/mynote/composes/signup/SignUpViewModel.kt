package com.example.mynote.composes.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynote.data.repositories.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: AccountRepository
): ViewModel() {
    private var _uiState = MutableStateFlow(SignUpUiState())
    val uiState = _uiState.asStateFlow()

    fun signUp(email: String, password: String)  {
        viewModelScope.launch {
            val result = repository.signUp(email, password)
            if (result) {
                _uiState.update {
                    it.copy(
                        isSignUp = true,
                        isSignUpSuccess = true,
                        resultMessage = "Sign up success"
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        isSignUp = true,
                        isSignUpSuccess = false,
                        resultMessage = "Sign up failed"
                    )
                }
            }
        }
    }
}