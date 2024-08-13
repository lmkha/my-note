package com.example.mynote

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mynote.composes.Screen
import com.example.mynote.composes.add_edit.AddEditNoteScreen
import com.example.mynote.composes.home.HomeScreen
import com.example.mynote.composes.login.LoginScreen
import com.example.mynote.composes.signup.SignUpScreen

@Composable
fun MyNoteApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(route = Screen.SignUp.route) {
            SignUpScreen(
                onSignUpSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.SignUp.route) { inclusive = true }
                    }
                }
            )
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
                onNavigateToAddNote = {
                    navController.navigate(Screen.AddNote.route)
                },
                onNavigateToEditNote = { note ->
                    navController.navigate("${Screen.EditNote.route}/${note.id}")
                },
                onSignOutNavigate = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }

        composable(route = Screen.AddNote.route) {
            AddEditNoteScreen(
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.AddNote.route) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = Screen.EditNote.route + "/{noteId}",
            arguments = listOf(
                navArgument("noteId") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { navBackStackEntry ->
            val noteId = navBackStackEntry.arguments?.getString("noteId")
            noteId?.let {
                AddEditNoteScreen(
                    sentNoteId = noteId,
                    onNavigateToHome = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.EditNote.route) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}
