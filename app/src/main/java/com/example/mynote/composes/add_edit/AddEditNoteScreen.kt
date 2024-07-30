package com.example.mynote.composes.add_edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun AddEditNoteScreen(
    viewModel: AddEditNoteViewModel = hiltViewModel(),
    onNavigateToHome: () -> Unit = {}
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    AddEditNoteScreenContent(
        uiState = uiState,
        onAddNewNote = viewModel::addNewNote,
        onNavigateToHome = onNavigateToHome
    )
}

@Composable
fun AddEditNoteScreenContent(
    uiState: AddEditUiState,
    onAddNewNote: (String, String) -> Unit = {_, _ -> },
    onNavigateToHome: () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LaunchedEffect(uiState.isAddEditNoteSuccess) {
            if (uiState.isAddEditNoteSuccess) {
                onNavigateToHome()
            }
        }

        var title by remember { mutableStateOf("") }
        var content by remember { mutableStateOf("") }

        Text(text = "Add/Edit Note")

        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text(text = "Title") }
        )

        TextField(
            value = content,
            onValueChange = { content = it },
            label = { Text(text = "Content") }
        )

        Button(onClick = {
            onAddNewNote(title, content)
        }) {
            Text(text = "Save")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddEditNoteScreenPreview() {
    AddEditNoteScreenContent(AddEditUiState())
}

