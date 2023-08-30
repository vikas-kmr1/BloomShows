package com.android.bloomshows.di.firebase


import android.content.Context
import com.android.bloomshows.R
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.android.bloomshows.network.services.auth.GoogleAuthUiClient
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth

    @Provides
    fun provideBeginSignInRequest(@ApplicationContext appContext: Context): BeginSignInRequest {
        return BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(appContext.getString(R.string.default_web_client_id))
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()

    }

    @Provides
    @Singleton
    fun provideGoogleAuthUiClient(
        @ApplicationContext appContext: Context,
        oneTapClient: SignInClient,
        buildSignInRequest: BeginSignInRequest,
        auth:FirebaseAuth
    ):GoogleAuthUiClient{
        return GoogleAuthUiClient(appContext, oneTapClient, buildSignInRequest,auth)
    }

    @Provides
    @Singleton
    fun provideOneTapClent(@ApplicationContext appContext: Context): SignInClient =
        Identity.getSignInClient(appContext.applicationContext)

//    @Provides
//    fun firestore(): FirebaseFirestore = Firebase.firestore
}


