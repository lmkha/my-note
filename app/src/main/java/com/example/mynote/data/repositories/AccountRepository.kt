package com.example.mynote.data.repositories

import com.example.mynote.data.models.User
import kotlinx.coroutines.flow.StateFlow

interface AccountRepository {
    val currentUser: StateFlow<User?>
    suspend fun signUp(userName:String, email: String, password: String): Boolean
    suspend fun loginWithEmailAndPassword(email: String, password: String): Boolean
    suspend fun signOut()
}