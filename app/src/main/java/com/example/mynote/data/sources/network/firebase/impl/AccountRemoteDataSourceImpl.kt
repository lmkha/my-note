package com.example.mynote.data.sources.network.firebase.impl

import android.util.Log
import com.example.mynote.data.models.User
import com.example.mynote.data.sources.network.firebase.AccountRemoteDataSource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AccountRemoteDataSourceImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AccountRemoteDataSource {
    companion object {
        private const val USER_COLLECTION = "users"
    }

    override val currentUserId: String  = auth.currentUser?.uid.orEmpty()
    override val hasUser: Boolean = auth.currentUser != null
    private val _currentUser: MutableStateFlow<User?> = MutableStateFlow(
        auth.currentUser?.let { User(it.uid, it.email.orEmpty()) }
    )
    override val currentUser: StateFlow<User?>  = _currentUser.asStateFlow()

    init {
        val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val firebaseUser = firebaseAuth.currentUser
            _currentUser.value = firebaseUser?.let { User(it.uid, it.email.orEmpty()) }
        }
        auth.addAuthStateListener(authStateListener)
    }

    override suspend fun loginWithEmailAndPassword(email: String, password: String): Boolean {
        try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            _currentUser.value = result.user?.let { User(it.uid, it.email.orEmpty()) }
            Log.i("CHECK_VAR", "Login, userId: ${currentUser.value!!.id}")
            return result.user != null
        } catch (throwable: Throwable) {
            Log.i("CHECK_VAR", "Login failed, message: ${throwable.message}")
        }
        return false
    }

    private suspend fun addUserToFireStore(user: User) {
        firestore.collection(USER_COLLECTION).document(user.id).set(user).await()
    }

    override suspend fun createAccount(email: String, password: String): Boolean {
        try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            _currentUser.value = result.user?.let {
                User(
                    id = it.uid,
                    email = it.email.orEmpty()
                )
            }
            if (result.user != null) {
                Log.i("CHECK_VAR", "Create account, userId: ${currentUser.value!!.id}")
                addUserToFireStore(currentUser.value!!)
                return true
            }
            return false
        } catch (throwable: Throwable) {
            Log.i("CHECK_VAR", "Write to FireStore failed, message: ${throwable.message}")
        }
        return false
    }

    override suspend fun signOut() {
        auth.signOut()
        _currentUser.value = null
    }
}