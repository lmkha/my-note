package com.example.mynote.data.repositories.impl

import com.example.mynote.data.models.User
import com.example.mynote.data.repositories.AccountRepository
import com.example.mynote.data.sources.network.firebase.AccountRemoteDataSource
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val accountRemoteDataSource: AccountRemoteDataSource
): AccountRepository {
    override val currentUser: StateFlow<User?> = accountRemoteDataSource.currentUser

    override suspend fun signUp(email: String, password: String): Boolean {
        return accountRemoteDataSource.createAccount(email, password)
    }

    override suspend fun loginWithEmailAndPassword(email: String, password: String): Boolean {
        return accountRemoteDataSource.loginWithEmailAndPassword(email, password)
    }

    override suspend fun signOut() {
        accountRemoteDataSource.signOut()
    }
}