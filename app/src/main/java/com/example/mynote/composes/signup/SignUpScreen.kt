package com.example.mynote.composes.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.mynote.R
import com.example.mynote.composes.common.CustomTextField
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
    onSignUp: (String, String, String) -> Unit,
    onSignUpSuccess: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LaunchedEffect(key1 = uiState.isSignUp, key2 = uiState.isSignUpSuccess) {
            if (uiState.isSignUp && uiState.isSignUpSuccess) {
                delay(1000)
                onSignUpSuccess()
            }

        }

        SignUpScreenAnimationIcon()

        var userName by remember {
            mutableStateOf("")
        }
        var email by remember {
            mutableStateOf("")
        }
        var password by remember {
            mutableStateOf("")
        }

        Text(
            text = "Create an account",
            modifier = Modifier.padding(
                top = 16.dp,
                bottom = 16.dp
            ),
            color = Color.Black,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold
        )

        CustomTextField(
            label = "Username",
            value = userName,
            focusManager = focusManager,
            onValueChange = { userName = it },
            isCapitalize = true
        )

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
            onValueChange = { password = it }
        )

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
                onSignUp(userName, email, password)
            },
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color.Black
            ),
        ) {
            Text(text = "Sign Up")
        }
    }
}

@Composable
private fun SignUpScreenAnimationIcon() {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.signup_animation)
    )
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
    LottieAnimation(
        modifier = Modifier.size(150.dp),
        composition = composition,
        progress = { progress }
    )
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
        onSignUp = { _, _, _ -> },
        onSignUpSuccess = { }
    )
}
