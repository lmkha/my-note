package com.example.mynote.composes.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val repository: NoteRepository,
    private val accountRepository: AccountRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState(
        userEmail = if (accountRepository.currentUser.value != null) accountRepository.currentUser.value!!.email else "",
    ))
    val uiState = _uiState.asStateFlow()

    fun signOut() {
        viewModelScope.launch {
            accountRepository.signOut()
            _uiState.update { it.copy(isSignOut = true) }
        }
    }
}