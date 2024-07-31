package com.example.mynote.data.sources.network.firebase

import com.example.mynote.data.models.User
import kotlinx.coroutines.flow.StateFlow

interface AccountRemoteDataSource {
    val currentUserId: String
    val hasUser: Boolean
    val currentUser: StateFlow<User?>
    suspend fun loginWithEmailAndPassword(email: String, password: String): Boolean
    suspend fun createAccount(userName:String, email: String, password: String): Boolean
    suspend fun signOut()
}