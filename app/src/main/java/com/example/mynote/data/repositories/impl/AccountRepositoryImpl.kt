package com.example.mynote.data.repositories.impl

import com.example.mynote.data.repositories.AccountRepository
import com.example.mynote.data.sources.network.firebase.AccountRemoteDataSource
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val accountRemoteDataSource: AccountRemoteDataSource
): AccountRepository {
    override suspend fun signUp(email: String, password: String): Boolean {
        return accountRemoteDataSource.createAccount(email, password)
    }
}