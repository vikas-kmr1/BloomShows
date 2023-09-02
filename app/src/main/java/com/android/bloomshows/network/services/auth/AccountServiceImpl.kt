package com.android.bloomshows.network.services.auth

import android.net.Uri
import com.android.bloomshows.network.model.User
import com.android.bloomshows.presentation.login_and_signup.utils.EmailVerfiedException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import kotlinx.coroutines.channels.awaitClose
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
        get() = auth.currentUser?.isEmailVerified ?: false

    override val userAnonymous: Boolean
        get() = auth.currentUser?.isAnonymous ?: false


    override val currentUser: Flow<User>
        get() = callbackFlow {
            val listener =
                FirebaseAuth.AuthStateListener { auth ->
                    this.trySend(auth.currentUser?.let { User(
                        userId = it.uid,
                        username = it.displayName,
                        profilePic = it.photoUrl?.toString(),
                        emailVerfied = it.isEmailVerified,
                        email = it.email?:"",
                        isAnonymous = it.isAnonymous,
                        createdAT = it.metadata?.creationTimestamp
                        ) } ?: User())
                }
            auth.addAuthStateListener(listener)
            awaitClose { auth.removeAuthStateListener(listener) }
        }

    //login
    override suspend fun authenticate(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).await()
        if (!emailVerfied) {
            throw EmailVerfiedException(
                "Email is not verified: Please verify your email to continue",
                Throwable(cause = Throwable(message = "UNVERIFIED-EMAIL"))
            )
        }
    }

    override suspend fun sendRecoveryEmail(email: String) {
        auth.sendPasswordResetEmail(email).await()
    }

    override suspend fun createAnonymousAccount() {
        val profileUpdates = userProfileChangeRequest {
            displayName = "Anonymous"
            photoUri =
                Uri.parse("https://firebasestorage.googleapis.com/v0/b/bloomshows.appspot.com/o/avatars%2Fanonymous.png?alt=media&token=c87a59f8-4d01-4292-a0c3-11361458a07d")
        }
        auth.signInAnonymously().await().user?.updateProfile(profileUpdates)?.await()
    }

    override suspend fun sendEmailVerification(email: String) {
        auth.currentUser?.sendEmailVerification()?.await()
    }

    //signUp
    override suspend fun createAccount(username: String, email: String, password: String) {

        val profileUpdates = userProfileChangeRequest {
            displayName = username
            photoUri =
                Uri.parse("https://firebasestorage.googleapis.com/v0/b/bloomshows.appspot.com/o/avatars%2Fdefault_user.gif?alt=media&token=306894de-8e42-4210-bd94-ae1c0c8b7fcb")
        }
        auth.createUserWithEmailAndPassword(email, password).await().user?.updateProfile(profileUpdates)?.await()
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


        //delete anonymous
        if(userAnonymous){
            deleteAccount()
        }
        // Sign the user back in anonymously.
        // createAnonymousAccount()
    }

}
