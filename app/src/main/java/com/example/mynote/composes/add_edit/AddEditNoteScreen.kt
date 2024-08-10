package com.example.mynote.composes.add_edit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mynote.R
import com.example.mynote.composes.common.AnimationIcon
import com.example.mynote.composes.common.CustomTextField
import com.example.mynote.data.models.Note

@Composable
fun AddEditNoteScreen(
    sentNote: Note? = null,
    viewModel: AddEditNoteViewModel = hiltViewModel(),
    onNavigateToHome: () -> Unit = {},
) {
    viewModel.modifyUiState(sentNote)
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    AddEditNoteScreenContent(
        uiState = uiState,
        onAddNewNote = viewModel::addNewNote,
        onUpdateNote = viewModel::updateNote,
        onNavigateToHome = onNavigateToHome
    )
}

@Composable
fun AddEditNoteScreenContent(
    uiState: AddEditUiState,
    onNavigateToHome: () -> Unit = {},
    onAddNewNote: (String, String) -> Unit = {_, _ -> },
    onUpdateNote: (Note) -> Unit = {},
) {
    LaunchedEffect(uiState.isAddEditNoteSuccess) {
        if (uiState.isAddEditNoteSuccess) {
            onNavigateToHome()
        }
    }

    val focusManager = LocalFocusManager.current
    val isAddNote = uiState.sentNote == null

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    uiState.sentNote?.let {
        title = it.title
        content = it.content
    }

    Scaffold(
        topBar = {
            AddEditScreenTopAppBar(
                onNavigateToHome = onNavigateToHome,
                tittle = if (isAddNote) "Add Note" else "Edit Note"
            )
        }
    ) { scaffoldPaddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize()
                .padding(scaffoldPaddingValues)
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
        ) {
            AnimationIcon(
                modifier = Modifier.padding(bottom = 16.dp),
                sourceId = if (isAddNote) R.raw.add_note_animation else R.raw.edit_note_animation,
                size = 170
            )
            CustomTextField(
                label = "Title",
                value = title,
                focusManager = focusManager,
                onValueChange = { title = it }
            )

            CustomTextField(
                label = "Content",
                value = content,
                focusManager = focusManager,
                onValueChange = { content = it }
            )

            Button(
                modifier = Modifier.padding(bottom = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isAddNote) Color.Green else Color(8, 135, 226),
                    contentColor = if (isAddNote) Color.Black else Color.White
                ),
                onClick = {
                    if (isAddNote) {
                        onAddNewNote(title, content)
                    } else {
                        onUpdateNote(
                            Note(
                                id = uiState.sentNote!!.id,
                                title = title,
                                content = content
                            )
                        )
                    }
                }
            ) {
                Text(text = if (isAddNote) "Add" else "Update")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditScreenTopAppBar(
    tittle: String = "Add Note",
    onNavigateToHome: () -> Unit = {}
) {
    TopAppBar(
        navigationIcon = {
            IconButton(
                onClick = { onNavigateToHome() }
            ) {
                Icon(
                    modifier = Modifier.size(40.dp),
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = null
                )
            }
        },
        title = {
            Text(text = tittle)
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun AddEditNoteScreenPreview() {
    AddEditNoteScreenContent(
        uiState = AddEditUiState(
            sentNote = Note(
                id = "9",
                title = "Complete the project",
                content = "Finish on time by following the best practices"
            )
        )
        // Check PowerShell
    )
}

