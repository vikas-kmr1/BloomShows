package com.android.bloomshows.network.services.auth

import com.android.bloomshows.network.model.User
import kotlinx.coroutines.flow.Flow

interface AccountService {
    val currentUserId: String
    val hasUser: Boolean

    val currentUser: Flow<User>

    suspend fun authenticate(email: String, password: String)
    suspend fun sendRecoveryEmail(email: String)
    suspend fun createAnonymousAccount()
    suspend fun linkAccount(email: String, password: String)
    suspend fun createAccount(email: String, password: String)
    suspend fun deleteAccount()
    suspend fun signOut()

        //Google
//    suspend fun signInWithIntent(intent: Intent): SignInResult
//    suspend fun signInWithGoogle(): IntentSender?
}
