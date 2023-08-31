package com.android.bloomshows.network.services.auth

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.android.bloomshows.network.model.SignInResult
import com.android.bloomshows.network.model.User
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await
import timber.log.Timber

class GoogleAuthUiClient(
    private val context: Context,
    private val oneTapClient: SignInClient,
    private val buildSignInRequest: BeginSignInRequest,
    private val auth: FirebaseAuth
) {
    suspend fun signIn(): IntentSender? {
        val result =
            oneTapClient.beginSignIn(
                buildSignInRequest()
            ).await()

        return result?.pendingIntent?.intentSender
    }

    suspend fun signInWithIntent(intent: Intent): SignInResult {
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
        return try {
            val user = auth.signInWithCredential(googleCredentials).await().user
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
        } catch (e: Exception) {
            SignInResult(
                data = null,
                errorMessage = e
            )
            throw e
        } catch (e: ApiException) {
            Timber.tag("One-Tap_Error").e(e)
            SignInResult(
                data = null,
                errorMessage = e
            )
            throw e
        }

    }

    suspend fun signOut() {
        try {
            oneTapClient.signOut().await()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
        }
    }

//    fun getSignedInUser(): User? = auth.currentUser?.run {
//        User(
//            userId = uid,
//            username = displayName,
//            profilePic = photoUrl?.toString()
//        )
//    }

    private fun buildSignInRequest(): BeginSignInRequest = buildSignInRequest
}