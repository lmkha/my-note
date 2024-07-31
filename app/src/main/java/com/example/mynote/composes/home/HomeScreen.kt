package com.example.mynote.composes.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mynote.composes.common.MyNoteAppAnimationIcon
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenContent(
    uiState: HomeUiState,
    onSignOut: () -> Unit,
    onNavigateToAddEditNote: () -> Unit,
    onSignOutNavigate: () -> Unit,
) {
    val scrollBarBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    LaunchedEffect(uiState.isSignOut) {
        if (uiState.isSignOut) {
            onSignOutNavigate()
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBarBehavior.nestedScrollConnection),
        topBar = {
            HomeScreenTopAppBar(
                scrollBehavior = scrollBarBehavior,
                onSignOut = onSignOut
            )
        },
        bottomBar = {},
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(16.dp),
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "${uiState.userName}'s notes",
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.headlineSmall
            )

            if (uiState.notes.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "No notes yet",
                        style = MaterialTheme.typography.headlineMedium,
                    )
                }
            } else {
                LazyColumn {
                    if (uiState.notes.isNotEmpty()) {
                        items(
                            items = uiState.notes,
                            key = { note -> note.id }
                        ) { note ->
                            NoteItem(note = note)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
    onSignOut: () -> Unit = {}
) {
    TopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                MyNoteAppAnimationIcon(
                    modifier = Modifier.padding(end = 8.dp),
                    size = 40
                )
                Text(
                    text = "MyNote",
                    style = MaterialTheme.typography.labelMedium
                )
            }
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
    Card(
        modifier = Modifier
            .height(90.dp)
            .width(350.dp)
            .padding(
                top = 8.dp,
                bottom = 8.dp,
                start = 16.dp,
                end = 16.dp
            ),
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = note.content,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreenContent(
//        uiState = HomeUiState(
//            notes = listOf(
//                Note(
//                    title = "English certificate",
//                    content = "Try to get 800"
//                ),
//                Note(
//                    title = "Android Dev",
//                    content = "Get a good job with 3000$ salary"
//                ),
//                Note(
//                    title = "Air Blade",
//                    content = "Buy a new one by the end of this year"
//                ),
//            ),
//        ),
        uiState = HomeUiState(),
        onSignOut = {},
        onNavigateToAddEditNote = {},
        onSignOutNavigate = {},
    )
}
