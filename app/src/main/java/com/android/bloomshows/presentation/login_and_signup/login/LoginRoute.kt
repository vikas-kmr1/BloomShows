package com.android.bloomshows.presentation.login_and_signup.login

import androidx.compose.runtime.Composable

@Composable
fun LoginRoute(
email: String? = null,
onLogInSubmitted: () -> Unit,
onNavigateToSignUp:()->Unit,
onNavUp: () -> Unit,

) {
    //val signInViewModel: SignInViewModel = viewModel(factory = SignInViewModelFactory())
    LoginScreen(
        email = email,
        onLogInSubmitted = { email, password ->
            // signInViewModel.signIn(email, password, onSignInSubmitted)
        },
        onNavUp = onNavUp,
        navToSignup = onNavigateToSignUp
    )
}