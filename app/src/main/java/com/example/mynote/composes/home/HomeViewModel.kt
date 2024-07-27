package com.example.mynote.composes.home

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
class HomeViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {
    private val _uiState = accountRepository.currentUser.value?.let {
        MutableStateFlow(HomeUiState(userEmail = it.email))
    } ?: MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            accountRepository.currentUser.collect { currentUser ->
                _uiState.update {
                    it.copy(isSignOut = currentUser == null)
                }
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            accountRepository.signOut()
        }
    }
}