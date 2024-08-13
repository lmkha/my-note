package com.example.mynote.composes.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mynote.composes.common.MyNoteAppAnimationIcon
import com.example.mynote.data.models.Note

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToEditNote: (Note) -> Unit = {},
    onNavigateToAddNote: () -> Unit = {},
    onSignOutNavigate: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    HomeScreenContent(
        uiState = uiState.value,
        onSignOut = viewModel::signOut,
        onNavigateToEditNote = onNavigateToEditNote,
        onNavigateToAddNote = onNavigateToAddNote,
        onDeleteNote = viewModel::deleteNote,
        onSignOutNavigate = onSignOutNavigate,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenContent(
    uiState: HomeUiState,
    onSignOut: () -> Unit,
    onNavigateToEditNote: (Note) -> Unit = {},
    onNavigateToAddNote: () -> Unit = {},
    onDeleteNote: (Note) -> Unit = {},
    onSignOutNavigate: () -> Unit,
) {
    LaunchedEffect(uiState.isSignOut) {
        if (uiState.isSignOut) {
            onSignOutNavigate()
        }
    }

    val scrollBarBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(modifier = Modifier.nestedScroll(scrollBarBehavior.nestedScrollConnection), topBar = {
        HomeScreenTopAppBar(
            scrollBehavior = scrollBarBehavior, onSignOut = onSignOut
        )
    }, bottomBar = {}, floatingActionButton = {
        FloatingActionButton(
            modifier = Modifier.padding(16.dp),
            onClick = { onNavigateToAddNote() },
            containerColor = Color(8, 135, 226),
            contentColor = Color.White,
            elevation = FloatingActionButtonDefaults.elevation(5.dp)
        ) {
            Icon(
                modifier = Modifier.size(30.dp),
                imageVector = Icons.Filled.Add,
                contentDescription = null
            )
        }
    }) { scaffoldPaddingValues ->
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
                        items(items = uiState.notes, key = { note -> note.id }) { note ->
                            NoteItem(
                                note = note,
                                onclick = { onNavigateToEditNote(note) },
                                onDeleteClick = { onDeleteNote(note) }
                            )
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
    TopAppBar(scrollBehavior = scrollBehavior, title = {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            MyNoteAppAnimationIcon(
                modifier = Modifier.padding(end = 8.dp), size = 40
            )
            Text(
                text = "MyNote", style = MaterialTheme.typography.labelMedium
            )
        }
    }, actions = {
        var expanded by remember {
            mutableStateOf(false)
        }

        IconButton(onClick = { expanded = true }) {
            Icon(
                imageVector = Icons.Filled.MoreVert, contentDescription = null
            )
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(text = { Text(text = "Logout") }, onClick = {
                expanded = false
                onSignOut()
            })
            DropdownMenuItem(text = { Text(text = "Manage account") }, onClick = { })
        }
    })
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun NoteItem(
    note: Note,
    onDeleteClick: () -> Unit = {},
    onLongClick: () -> Unit = {},
    onclick: () -> Unit = {},
) {
    val dismissState = rememberSwipeToDismissBoxState(
        initialValue = SwipeToDismissBoxValue.Settled,
//        initialValue = if (note.id == "2") SwipeToDismissBoxValue.EndToStart else SwipeToDismissBoxValue.Settled,
        positionalThreshold = { it * 0.25f },
        confirmValueChange = { state ->
            when (state) {
                SwipeToDismissBoxValue.EndToStart -> {
                    true
                }

                else -> false
            }
        },
    )

    val cardHeight = 90.dp
    val cardWidth = 350.dp
    val cardPadding = PaddingValues(
        top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp
    )
    val cardShape = RoundedCornerShape(8.dp)

    SwipeToDismissBox(
        modifier = Modifier
            .padding(
                start = 16.dp,
                end = 16.dp,
            )
            .clip(RoundedCornerShape(16.dp)),
        state = dismissState,
        backgroundContent = {
            if (dismissState.targetValue == SwipeToDismissBoxValue.EndToStart) {
                val color: Color = Color.Red
                val icon: @Composable () -> Unit = {
                    Icon(
                        imageVector = Icons.Filled.Delete, contentDescription = "Delete wish"
                    )
                }
                
                Card(
                    onClick = { },
                    modifier = Modifier
                        .height(cardHeight)
                        .width(cardWidth)
                        .padding(cardPadding)
                        .clip(cardShape),
                    shape = cardShape,
                    colors = CardDefaults.cardColors(
                        containerColor = color
                    )
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End
                        ) {
                            if (dismissState.currentValue == SwipeToDismissBoxValue.EndToStart) {
                                Card(
                                    modifier = Modifier
                                        .height(cardHeight)
                                        .width(250.dp)
                                        .clip(cardShape)
                                        .combinedClickable(
                                            onClick = { onclick() },
                                            onLongClick = { onLongClick() }),
                                    shape = cardShape,
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color(181, 223, 252)
                                    ),
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
                                            style = MaterialTheme.typography.bodyMedium,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }
                            }
                            IconButton(
                                modifier = Modifier.width(68.dp).height(73.dp),
                                onClick = { onDeleteClick() }
                            ) {
                                icon()
                            }
                        }


                    }
                }

            }
        }
    ) {
        Card(
            modifier = Modifier
                .height(cardHeight)
                .width(cardWidth)
                .padding(cardPadding)
                .clip(cardShape)
                .combinedClickable(onClick = { onclick() }, onLongClick = { onLongClick() }),
            shape = cardShape,
            colors = CardDefaults.cardColors(
                containerColor = Color(181, 223, 252)
            ),
        ) {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = note.title, style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = note.content,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreenContent(
        uiState = HomeUiState(
            notes = listOf(
                Note(
                    id = "1", title = "English certificate", content = "Try to get 800"
                ),
                Note(
                    id = "2", title = "Android Dev", content = "Get a good job with 3000$ salary"
                ),
                Note(
                    id = "3",
                    title = "Air Blade",
                    content = "Buy a new one by the end of this year buy a new one by the end of this year Buy a new one by the end of this year"
                ),
            ),
        ),
        onSignOut = {},
        onNavigateToAddNote = {},
        onNavigateToEditNote = {},
        onSignOutNavigate = {},
    )
}
