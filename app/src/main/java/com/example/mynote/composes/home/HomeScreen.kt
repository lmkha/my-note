package com.example.mynote.composes.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    HomeScreenContent(
        uiState = uiState.value,
        onSignOut = viewModel::signOut,
        onNavigateToAddEditNote = onNavigateToAddEditNote,
        onSignOutNavigate = onSignOutNavigate,
    )
}

@Composable
fun HomeScreenContent(
    uiState: HomeUiState,
    onSignOut: () -> Unit,
    onNavigateToAddEditNote: () -> Unit,
    onSignOutNavigate: () -> Unit,
) {
    Scaffold(
        topBar = {
            HomeScreenTopAppBar(onSignOut = onSignOut)
     },
        bottomBar = {},
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNavigateToAddEditNote() }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = null
                )
            }
        }
    ) { scaffoldPaddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPaddingValues),
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
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenTopAppBar(
    onSignOut: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(text = "Home")
        },
        actions = {
            var expanded by remember {
                mutableStateOf(false)
            }

            IconButton(
                onClick = { expanded = true }
            ) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = null
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text(text = "Logout") },
                    onClick = {
                        expanded = false
                        onSignOut()
                    }
                )
                DropdownMenuItem(
                    text = { Text(text = "Manage account") },
                    onClick = {  }
                )
            }
        }
    )
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
    )
}