package com.android.bloomshows.presentation.home

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.bloomshows.presentation.login_and_signup.login.LoginViewModel


@Composable
fun HomeRoute(
    loginViewModel: LoginViewModel = hiltViewModel(),
    navigate_to_login: () -> Unit
) {
    HomeScreen(onLogOut = {loginViewModel.logOut(navigate_to_login)})
}