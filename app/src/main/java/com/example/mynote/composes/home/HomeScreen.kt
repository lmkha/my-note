package com.example.mynote.composes.home

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mynote.composes.common.MyNoteAppAnimationIcon
import com.example.mynote.data.models.Note
import kotlinx.coroutines.launch

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
        onDeleteNote = viewModel::deleteNote,
        onChangeIsDone = viewModel::changeIsDone,
        onNavigateToEditNote = onNavigateToEditNote,
        onNavigateToAddNote = onNavigateToAddNote,
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
    onChangeIsDone: (Note) -> Unit = {},
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
                            Row {
                                NoteItem(
                                    note = note,
                                    onclick = { onNavigateToEditNote(note) },
                                    onDeleteClick = { onDeleteNote(note) },
                                    oneChangeIsDone = { onChangeIsDone(note) }
                                )
                            }
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

@Composable
fun NoteItem(
    note: Note,
    onDeleteClick: () -> Unit = {},
    onclick: () -> Unit = {},
    oneChangeIsDone: () -> Unit = {}
) {
    val offsetX = remember { Animatable(0f) }
    var swipedToDismiss by remember { mutableStateOf(false) }
    val dragSpeedFactor = 0.3f
    val deleteRange = 150f
    val recoverRange = 10f
    var isDragLeftToRight = false
    val scope = rememberCoroutineScope()
    val cardWidth by animateDpAsState(targetValue = if (swipedToDismiss) 250.dp else 379.dp,
        label = ""
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 8.dp,
                bottom = 8.dp,
                start = 16.dp,
                end = 16.dp
            )
            .clip(RoundedCornerShape(20.dp))
            .background(Color(255, 140, 158))
            .clickable { onDeleteClick() },
    ) {
        Row(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color(255, 140, 158)),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(100.dp),
                onClick = { }
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete, contentDescription = "Delete wish"
                )
            }
        }
        Box(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
                .offset(x = offsetX.value.dp)
                .clip(RoundedCornerShape(8.dp))
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragEnd = {
                            val targetValue = if(!swipedToDismiss && isDragLeftToRight)
                                offsetX.targetValue.coerceIn(-recoverRange, 0f)
                            else offsetX.targetValue.coerceIn(0f, deleteRange)

                            if (offsetX.targetValue != targetValue) {
                                // Recovery width if not drag enough
                                scope.launch {
                                    offsetX.animateTo(
                                        targetValue,
                                        animationSpec = tween(
                                            durationMillis = 300,
                                            easing = LinearOutSlowInEasing
                                        )
                                    )
                                }
                            }
                        },
                        onDrag = { change, dragAmount ->
                            if (!swipedToDismiss) {
                                if (dragAmount.x < 0) {
                                    isDragLeftToRight = false
                                    val newOffsetX =
                                        offsetX.targetValue + dragAmount.x * dragSpeedFactor
                                    if (-newOffsetX <= deleteRange) {
                                        scope.launch {
                                            offsetX.snapTo(newOffsetX)
                                        }
                                    } else {
                                        swipedToDismiss = true
                                    }
                                }
                            } else {
                                // When swiped to dismiss, recover the card when drag back
                                if (dragAmount.x > 0) {
                                    isDragLeftToRight = true
                                    val newOffsetX =
                                        offsetX.targetValue + dragAmount.x * dragSpeedFactor
                                    if (newOffsetX <= recoverRange) {
                                        scope.launch {
                                            offsetX.snapTo(newOffsetX)
                                        }
                                    } else {
                                        swipedToDismiss = false
                                    }
                                }
                            }
                            change.consume()
                        }
                    )
                }
        ) {
            Card(
                modifier = Modifier
                    .height(100.dp)
                    .width(cardWidth)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { onclick() },
                colors = CardDefaults.cardColors(
                    containerColor = if (note.done)
                        Color(119, 228, 200)
                    else Color(181, 223, 252)
                )
            ) {
                Row(
                    modifier = Modifier
                    .height(100.dp)
                        .width(cardWidth),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (!swipedToDismiss) {
                        Checkbox(
                            checked = note.done,
                            onCheckedChange = {
                                oneChangeIsDone()
                            }
                        )
                    }
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
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
                    done = true,
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
