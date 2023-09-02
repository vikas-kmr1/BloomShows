package com.android.bloomshows.presentation.login_and_signup.login

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.bloomshows.network.model.SignInResult
import com.google.firebase.FirebaseException
import kotlinx.coroutines.CancellationException
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

    //Activity launcer for Google SignIn
    val launcherGoogle = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                scope.launch {
                    if (result.resultCode == Activity.RESULT_OK) {
                        scope.launch {
                            try{
                            val signInResult = googleAuthUiClient.signInWithIntent(
                                intent = result.data ?: return@launch
                            )
                            logInViewModel.onSigninResult(signInResult)}
                            catch (f : FirebaseException){
                                logInViewModel.onSigninResult(
                                    SignInResult(
                                        data = null,
                                        errorMessage = f
                                    )
                                )
                            }
                        }
                    }
                }
            } else {
                logInViewModel.onSigninResult(
                    SignInResult(
                        data = null,
                        errorMessage = CancellationException()
                    )
                )
            }
        }
    )

    LoginScreen(
        email = "",
        onLogInSubmitted = { email, password ->
            logInViewModel.logIn(
                email = email,
                password = password
            )
        },
        navToSignup = navigateToSignUp,
        navToForgot = navigateToForgot,
        navToHome = {
            scope.launch {
                navigateToHome()
                logInViewModel.resetState()
            }
        },
        onGoogleSignInClicked = { logInViewModel.logInWithGoogle(launcherGoogle) },
        onAnonymousSignInClicked = logInViewModel::signInAsAnonuymous,
        loginUIState = loginState,
    )
}





