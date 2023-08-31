package com.android.bloomshows.presentation.login_and_signup.forgot_password

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.play.integrity.internal.f

@Composable
fun Forgot_password_route(
    onNavigateUp:()->Unit,
    forgotViewModel: ForgotViewModel = hiltViewModel()
    ) {
    val forgotUiState = forgotViewModel.forgotUiState
    Forgot_password_Screen(
        onSubmitted = {  email->
            forgotViewModel.sendResetCredentialMail(email)
        },
        navigateUp = onNavigateUp,
        forgotUiState = forgotUiState,
        resetUiState = forgotViewModel::resetState
    )
}