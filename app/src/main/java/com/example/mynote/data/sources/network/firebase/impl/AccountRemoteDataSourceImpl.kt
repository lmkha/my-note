package com.example.mynote.data.sources.network.firebase.impl

import android.util.Log
import com.example.mynote.data.models.User
import com.example.mynote.data.sources.network.firebase.AccountRemoteDataSource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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
    private val _currentUser: MutableStateFlow<User?> = MutableStateFlow(User())
    override val currentUser: StateFlow<User?>  = _currentUser.asStateFlow()

    init {
        CoroutineScope(Dispatchers.Main).launch {
            _currentUser.value = auth.currentUser?.let {
                val firestoreUser = firestore
                    .collection(USER_COLLECTION)
                    .document(it.uid)
                    .get()
                    .await()
                    .toObject(User::class.java)
                firestoreUser ?: User()
            }
        }

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
            return result.user != null
        } catch (throwable: Throwable) {
            Log.i("CHECK_VAR", "Login failed, message: ${throwable.message}")
        }
        return false
    }

    private suspend fun addUserToFireStore(user: User) {
        firestore.collection(USER_COLLECTION).document(user.id).set(user).await()
    }

    override suspend fun createAccount(userName: String, email: String, password: String): Boolean {
        try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            _currentUser.value = result.user?.let {
                User(
                    name = userName,
                    id = it.uid,
                    email = it.email.orEmpty()
                )
            }

            if (result.user != null) {
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