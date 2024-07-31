package com.example.mynote.composes.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mynote.composes.common.CustomTextField
import com.example.mynote.composes.common.MyNoteAppAnimationIcon

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
private fun LoginScreenContent(
    uiState: LoginUiState,
    onLogin: (String, String) -> Unit,
    navigateSignUp: () -> Unit,
    navigateToHome: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LaunchedEffect(uiState.isLoginSuccess) {
            if (uiState.isLoginSuccess) {
                navigateToHome()
            }
        }

        MyNoteAppAnimationIcon(size = 150)

        Row {
            Text(
                text = "Welcome to ",
                modifier = Modifier.padding(
                    top = 16.dp,
                    bottom = 16.dp
                ),
                color = Color.Black,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "MyNote",
                modifier = Modifier.padding(
                    top = 16.dp,
                    bottom = 16.dp
                ),
                color = Color(red = 61, green = 177, blue = 255),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold
            )
        }

        var email by remember {
            mutableStateOf("")
        }
        var password by remember {
            mutableStateOf("")
        }

        CustomTextField(
            label = "Email",
            value = email,
            focusManager = focusManager,
            onValueChange = { email = it }
        )

        CustomTextField(
            label = "Password",
            value = password,
            focusManager = focusManager,
            onValueChange = { password = it },
            isPassword = true
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (uiState.isLoginSuccess) {
                Text(text = "Login success")
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = "Success",
                    tint = Color.Green
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 45.dp),
            horizontalAlignment = Alignment.End
        ) {
            TextButton(
                onClick = {
                    navigateSignUp()
                },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = Color.Black,
                )
            ) {
                Text(text = "Create Account")
            }
        }

        Button(
            onClick = {
                onLogin(email, password)
            },
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color.Black
            ),
        ) {
            Text(text = "Login")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    LoginScreenContent(
        uiState = LoginUiState(
        ),
        onLogin = { _, _ -> },
        navigateToHome = { },
        navigateSignUp = { }
    )
}

