package com.android.bloomshows.network.services.auth

import android.net.Uri
import com.android.bloomshows.network.model.User
import com.android.bloomshows.presentation.login_and_signup.utils.EmailVerfiedException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.actionCodeSettings
import com.google.firebase.auth.ktx.userProfileChangeRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AccountServiceImpl @Inject constructor(private val auth: FirebaseAuth) :
    AccountService {
    override val currentUserId: String
        get() = auth.currentUser?.uid.orEmpty()

    override val hasUser: Boolean
        get() = auth.currentUser != null
    override val emailVerfied: Boolean
        get() = auth.currentUser?.isEmailVerified ?:false


    override val currentUser: Flow<User> = callbackFlow {
        FirebaseAuth.AuthStateListener { auth ->
            auth.currentUser?.run {
                User(
                    userId = uid,
                    username = displayName,
                    profilePic = photoUrl?.toString(),
                    emailVerfied = isEmailVerified,
                    email = email
                )
            }
        }
    }

    //login
    override suspend fun authenticate(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).await()
        if(!emailVerfied){
            throw EmailVerfiedException(
                "Email is not verified: Please verify your email to continue",
                Throwable(cause = Throwable(message = "UNVERIFIED-EMAIL")))
        }
    }

    override suspend fun sendRecoveryEmail(email: String) {
        auth.sendPasswordResetEmail(email).await()
    }

    override suspend fun createAnonymousAccount() {
        auth.signInAnonymously().await()
    }

    override suspend fun sendEmailVerification(email: String) {
        auth.currentUser?.sendEmailVerification()?.await()
    }

    //signUp
    override suspend fun createAccount(username: String, email: String, password: String) {

        val profileUpdates = userProfileChangeRequest {
            displayName = username
            photoUri = Uri.parse("https://example.com/jane-q-user/profile.jpg")
        }
        auth.createUserWithEmailAndPassword(email, password).await().user?.updateProfile(
            profileUpdates
        )
        sendEmailVerification(email)
    }

    override suspend fun deleteAccount() {
        auth.currentUser!!.delete().await()
    }

    override suspend fun signOut() {
        if (auth.currentUser!!.isAnonymous) {
            auth.currentUser!!.delete()
        }
        auth.signOut()

        // Sign the user back in anonymously.
        //createAnonymousAccount()
    }

}
