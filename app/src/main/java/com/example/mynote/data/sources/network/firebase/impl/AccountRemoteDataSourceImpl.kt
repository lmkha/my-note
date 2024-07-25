package com.example.mynote.data.sources.network.firebase.impl

import com.example.mynote.data.models.User
import com.example.mynote.data.sources.network.firebase.AccountRemoteDataSource
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AccountRemoteDataSourceImpl @Inject constructor(private val auth: FirebaseAuth) : AccountRemoteDataSource {
    override val currentUserId: String  = auth.currentUser?.uid.orEmpty()
    override val hasUser: Boolean = auth.currentUser != null
    private val _currentUser = MutableStateFlow<User?>(null)
    override val currentUser: StateFlow<User?>  = _currentUser.asStateFlow()

    init {
        val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val firebaseUser = firebaseAuth.currentUser
            _currentUser.value = firebaseUser?.let { User(it.uid, it.email.orEmpty()) }
        }
        auth.addAuthStateListener(authStateListener)
    }

    override suspend fun authenticate(email: String, password: String): Boolean {
        return true
    }

    override suspend fun createAccount(email: String, password: String): Boolean {
        try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            return result.user != null
        } catch (throwable: Throwable) {
            println(throwable.message)
        }
        return false
    }
}