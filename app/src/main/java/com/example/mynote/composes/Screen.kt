package com.example.mynote.composes

sealed class Screen(val route: String) {
    data object Welcome : Screen("welcome")
    data object SignUp : Screen("signUp")
    data object Login : Screen("login")
    data object Home : Screen("home")
    data object AddNote : Screen("addNote")
    data object EditNote : Screen("editNote")
}
