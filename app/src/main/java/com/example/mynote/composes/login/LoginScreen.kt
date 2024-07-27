package com.example.mynote.composes.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
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

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navigateToHome: () -> Unit,
    navigateSignUp: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    LoginScreenContent(
        uiState = uiState,
        onLogin = viewModel::login,
        navigateToHome = navigateToHome,
        navigateSignUp = navigateSignUp
    )
}

@Composable
fun LoginScreenContent(
    uiState: LoginUiState,
    onLogin: (String, String) -> Unit,
    navigateSignUp: () -> Unit,
    navigateToHome: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (uiState.isLoginSuccess) {
            navigateToHome()
        }

        Text(text = "Login Screen")
        var email by remember {
            mutableStateOf("")
        }
        var password by remember {
            mutableStateOf("")
        }

        TextField(
            label = { Text("Email") },
            value = email,
            onValueChange = { email = it }
        )

        TextField(
            label = { Text("Password") },
            value = password,
            onValueChange = { password = it }
        )

        Column(
            modifier = Modifier.fillMaxWidth().padding(end = 45.dp),
            horizontalAlignment = Alignment.End
        ) {
            TextButton(onClick = {
                navigateSignUp()
            }) {
                Text(text = "Create Account")
            }

            TextButton(onClick = { /*TODO*/ }) {
                Text(text = "Forgot Password")
            }
        }

        Button(onClick = {
            onLogin(email, password)
        }) {
            Text(text = "Login")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreenContent(
        uiState = LoginUiState(),
        onLogin = { _, _ -> },
        navigateToHome = { },
        navigateSignUp = { }
    )
}

