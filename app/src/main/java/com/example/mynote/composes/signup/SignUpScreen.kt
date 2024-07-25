package com.example.mynote.composes.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay

@Composable
fun SignUpScreen(viewModel: SignUpViewModel = hiltViewModel(), onSignUpSuccess: () -> Unit){
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    SignUpScreenContent(
        uiState = uiState,
        onSignUp = viewModel::signUp,
        onSignUpSuccess = onSignUpSuccess
    )
}

@Composable
fun SignUpScreenContent(
    uiState: SignUpUiState,
    onSignUp: (String, String) -> Unit,
    onSignUpSuccess: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var email by remember {
            mutableStateOf("")
        }
        var password by remember {
            mutableStateOf("")
        }

        Text(text = "Sign Up")

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password") }
        )

        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = uiState.resultMessage)
            if (uiState.isSignUpSuccess) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = "Success",
                    tint = Color.Green
                )
            } else if (uiState.isSignUp) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = "Error",
                    tint = Color.Red
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                onSignUp(email, password)
            }
        ) {
            Text(text = "Sign Up")
        }

        if (uiState.isSignUp && uiState.isSignUpSuccess) {
            LaunchedEffect(key1 = Unit) {
                delay(2000)
                onSignUpSuccess()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    SignUpScreenContent(
        uiState = SignUpUiState(
            isSignUp = true,
            isSignUpSuccess = true,
            resultMessage = "Success"
        ),
        onSignUp = { _, _ -> },
        onSignUpSuccess = { }
    )
}
