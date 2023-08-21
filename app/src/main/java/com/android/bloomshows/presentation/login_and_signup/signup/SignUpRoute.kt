package com.android.bloomshows.presentation.login_and_signup.signup

import androidx.compose.runtime.Composable

@Composable
fun SignUpRoute(
    email: String? = null,
    onSignUpSubmitted: () -> Unit,
    onNavigateToLogin:()->Unit,

    ) {
    //val signInViewModel: SignInViewModel = viewModel(factory = SignInViewModelFactory())
    SignUpScreen(
        onSignUpSubmitted = { name, email, password ->
            // signInViewModel.signIn(email, password, onSignInSubmitted)
        },
        navToLogin = onNavigateToLogin
    )
}