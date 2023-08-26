package com.android.bloomshows.presentation.login_and_signup.signup

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SignUpRoute(
    email: String? = null,
    onSignUpSubmitted: () -> Unit,
    onNavigateToLogin:()->Unit,
    signUpViewModel: SignUpViewModel = hiltViewModel()
    ) {

    SignUpScreen(
        onSignUpSubmitted = { name, email, password ->
             signUpViewModel.signUp(email = email, password = password, username =name,  onSignUpCompleted =  onSignUpSubmitted)
        },
        navToLogin = onNavigateToLogin
    )
}