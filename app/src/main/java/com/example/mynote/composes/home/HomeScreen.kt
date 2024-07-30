package com.example.mynote.composes.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mynote.data.models.Note

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToAddEditNote: () -> Unit,
    onSignOutNavigate: () -> Unit
) {
    Log.i("CHECK_VAR", "HomeScreen")
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    HomeScreenContent(
        uiState = uiState.value,
        onSignOut = viewModel::signOut,
        onNavigateToAddEditNote = onNavigateToAddEditNote,
        onSignOutNavigate = onSignOutNavigate,
        addDefaultNote = viewModel::addNewNote,
        onShowInfo = viewModel::showNotesLogInfo
    )
}

@Composable
fun HomeScreenContent(
    uiState: HomeUiState,
    onShowInfo: () -> Unit = {},
    onSignOut: () -> Unit,
    onNavigateToAddEditNote: () -> Unit,
    onSignOutNavigate: () -> Unit,
    addDefaultNote: () -> Unit
) {
    Log.i("CHECK_VAR", "HomeScreenContent")
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LaunchedEffect(uiState.isSignOut) {
            if (uiState.isSignOut) {
                onSignOutNavigate()
            }
        }

        Text(text = "Welcome ${uiState.userEmail} to MyNote")

        LazyColumn {
            if (uiState.notes.isNotEmpty()) {
                items(uiState.notes) { note ->
                    NoteItem(note = note)
                }
            }
        }

        Button(onClick = {
            onSignOut()
        }) {
            Text(text = "Logout")
        }

        Button(onClick = {
            onNavigateToAddEditNote()
        }) {
            Text(text = "Add New Note")
        }

        Button(onClick = {
            addDefaultNote()
        }) {
            Text(text = "Add default Note")
        }

        Button(onClick = { onShowInfo() }) {
            Text(text = "Show info")
        }
    }
}

@Composable
private fun NoteItem(note: Note) {
    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        Text(text = note.title)
        Text(text = note.content)
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreenContent(
        uiState = HomeUiState(),
        onSignOut = {},
        onNavigateToAddEditNote = {},
        onSignOutNavigate = {},
        addDefaultNote = {}
    )
}