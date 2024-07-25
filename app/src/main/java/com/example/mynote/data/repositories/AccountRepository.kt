package com.example.mynote.data.repositories

interface AccountRepository {
    suspend fun signUp(email: String, password: String): Boolean
}