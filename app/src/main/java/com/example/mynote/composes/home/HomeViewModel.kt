package com.example.mynote.composes.home

import androidx.lifecycle.ViewModel
import com.example.mynote.data.repositories.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(repository: NoteRepository): ViewModel() {
}