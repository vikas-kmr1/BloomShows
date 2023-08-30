package com.android.bloomshows.presentation.login_and_signup.login

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch

@Composable
fun LoginRoute(
    navigateToSignUp: () -> Unit,
    navigateToForgot: () -> Unit,
    navigateToHome: () -> Unit,
    logInViewModel: LoginViewModel = hiltViewModel()
) {

    val googleAuthUiClient = logInViewModel.googleAuthUiClient

    val scope = rememberCoroutineScope()
    val loginState = logInViewModel.logInUiState

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->

            if (result.resultCode == Activity.RESULT_OK) {
                scope.launch {
                    if (result.resultCode == Activity.RESULT_OK) {
                        scope.launch {
                            val signInResult = googleAuthUiClient.signInWithIntent(
                                intent = result.data ?: return@launch
                            )
                            logInViewModel.onGoogleSigninResult(signInResult)
                        }
                    }
                }
            }
        }
    )


    LoginScreen(
        email = "",
        onLogInSubmitted = { email, password ->
            logInViewModel.logIn(
                email = email,
                password = password)
        },
        navToSignup = navigateToSignUp,
        navToForgot = navigateToForgot,
        navToHome = {
            scope.launch{
                navigateToHome()
                logInViewModel.resetState()
            }
        },
        onGoogleSignInClicked = { logInViewModel.logInWithGoogle(launcher) },
        onFaceBookSignInClicked = {},
        loginUIState = loginState,
        resetUiState = logInViewModel::resetState
    )
}





