package com.android.bloomshows.network.services.auth

import com.android.bloomshows.network.model.User
import kotlinx.coroutines.flow.Flow

interface AccountService {
    val currentUserId: String
    val hasUser: Boolean

    val currentUser: Flow<User>
    val emailVerfied: Boolean

    suspend fun authenticate(email: String, password: String)
    suspend fun sendRecoveryEmail(email: String)
    suspend fun sendEmailVerification(email: String)
    suspend fun createAnonymousAccount()
    suspend fun createAccount(username:String,email: String, password: String)

    suspend fun deleteAccount()
    suspend fun signOut()

        //Google
//    suspend fun signInWithIntent(intent: Intent): SignInResult
//    suspend fun signInWithGoogle(): IntentSender?
}
