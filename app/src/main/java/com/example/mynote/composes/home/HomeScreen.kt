package com.example.mynote.composes.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onSignOutNavigate: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    HomeScreenContent(
        uiState = uiState,
        onSignOut = viewModel::signOut,
        onSignOutNavigate = onSignOutNavigate
    )
}

@Composable
fun HomeScreenContent(
    uiState: HomeUiState,
    onSignOut: () -> Unit,
    onSignOutNavigate: () -> Unit
) {
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
        Button(onClick = {
            onSignOut()
        }) {
            Text(text = "Logout")
        }
    }
}