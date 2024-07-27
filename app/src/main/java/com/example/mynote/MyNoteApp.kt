package com.example.mynote

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mynote.composes.Screen
import com.example.mynote.composes.home.HomeScreen
import com.example.mynote.composes.login.LoginScreen
import com.example.mynote.composes.signup.SignUpScreen

@Composable
fun MyNoteApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(route = Screen.SignUp.route) {
            SignUpScreen(onSignUpSuccess = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.SignUp.route) { inclusive = true }
                }
            })
        }
        composable(route = Screen.Login.route) {
            LoginScreen(
                navigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                navigateSignUp = {
                    navController.navigate(Screen.SignUp.route)
                }
            )
        }
        composable(route = Screen.Home.route) {
            HomeScreen(
                onSignOutNavigate = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }
    }
}
