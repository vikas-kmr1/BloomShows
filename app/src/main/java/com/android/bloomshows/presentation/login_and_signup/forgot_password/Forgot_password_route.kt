package com.android.bloomshows.presentation.login_and_signup.forgot_password

import androidx.compose.runtime.Composable

@Composable
fun Forgot_password_route(
    onNavigateUp:()->Unit,
    ) {
    //val signInViewModel: SignInViewModel = viewModel(factory = SignInViewModelFactory())
    Forgot_password_Screen(
        onSubmitted = {  email->
            // signInViewModel.signIn(email, password, onSignInSubmitted)
        },
        navigateUp = onNavigateUp
    )
}