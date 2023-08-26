package com.android.bloomshows.presentation.login_and_signup.login

import GoogleAuthUiClient
import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch

@Composable
fun LoginRoute(
    email: String? = null,
    onLogInSubmitted: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    onNavigateToForgot: () -> Unit,
    logInViewModel: LoginViewModel = hiltViewModel()
) {
    val activity = LocalContext.current as Activity
    val googleAuthUiClient = GoogleAuthUiClient(
        context = activity,
        oneTapClient = Identity.getSignInClient(activity),
    )

    val scope = rememberCoroutineScope()
    val viewModel = viewModel<SignInViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        if (googleAuthUiClient.getSignedInUser() != null) {
            onLogInSubmitted()
        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->

            if (result.resultCode == Activity.RESULT_OK) {
                scope.launch {
                    val signInResult = googleAuthUiClient.signInWithIntent(
                        intent = result.data ?: return@launch
                    )
                }

            }

        }

    )

    LaunchedEffect(key1 = state.isSignInSuccessful) {
        if (state.isSignInSuccessful) {

            onLogInSubmitted()
            viewModel.resetState()
        }
    }
    LoginScreen(
        email = email,
        onLogInSubmitted = { email, password ->
            logInViewModel.logIn(
                email = email,
                password = password,
                onLoginCompleted = onLogInSubmitted
            )
        },
        navToSignup = onNavigateToSignUp,
        navToForgot = onNavigateToForgot,
        onGoogleSignInClicked = {
            scope.launch {
                val signInIntentSender = googleAuthUiClient.signIn()
                launcher.launch(
                    IntentSenderRequest.Builder(
                        signInIntentSender ?: return@launch
                    ).build()
                )
            }
        },
        onFaceBookSignInClicked =  {},
        state = state
    )
}





