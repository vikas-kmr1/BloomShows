package com.android.bloomshows.network.services.auth

import com.android.bloomshows.network.model.SignInResult
import com.android.bloomshows.network.model.User
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class AccountServiceImpl @Inject constructor(private val auth: FirebaseAuth) :
    AccountService {
    override val currentUserId: String
        get() = auth.currentUser?.uid.orEmpty()

    override val hasUser: Boolean
        get() = auth.currentUser != null

    override val currentUser: Flow<User> = callbackFlow {
        FirebaseAuth.AuthStateListener { auth ->
            auth.currentUser?.run {
                User(
                    userId = uid,
                    username = displayName,
                    profilePic = photoUrl?.toString()
                )
            }
        }
    }

    //login
    override suspend fun authenticate(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun sendRecoveryEmail(email: String) {
        auth.sendPasswordResetEmail(email).await()
    }

    override suspend fun createAnonymousAccount() {
        auth.signInAnonymously().await()
    }

    override suspend fun linkAccount(email: String, password: String) {
        val credential = EmailAuthProvider.getCredential(email, password)
        Firebase.auth.currentUser!!.linkWithCredential(credential)
            .addOnCompleteListener { }
    }

    //signUp
    override suspend fun createAccount(email: String, password: String) {
        try {
            val user = auth.createUserWithEmailAndPassword(email, password).await().user
            SignInResult(
                data = user?.run {
                    User(
                        userId = uid,
                        username = displayName,
                        profilePic = photoUrl?.toString()
                    )
                },
                errorMessage = null
            )

        }catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            SignInResult(
                data = null,
                errorMessage = e
            )
        } catch (e: ApiException) {
            Timber.tag("One-Tap_Error").e(e)
            SignInResult(
                data = null,
                errorMessage = e
            )
        }
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

    companion object {
        private const val LINK_ACCOUNT_TRACE = "linkAccount"
    }
}
