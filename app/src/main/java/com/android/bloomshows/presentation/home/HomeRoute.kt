package com.android.bloomshows.presentation.home

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.android.bloomshows.navigation.MoreMovieDestination
import com.android.bloomshows.navigation.MovieDetailsDestination
import com.android.bloomshows.navigation.MovieDetailsDestination.id
import com.android.bloomshows.navigation.navigateSingleTopTo
import com.android.bloomshows.presentation.login_and_signup.login.LoginViewModel
import com.android.bloomshows.utils.MediaCategories
import okhttp3.Route


@Composable
fun HomeRoute(
    loginViewModel: LoginViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel(),
    navigate_to_login: () -> Unit,
    navController: NavHostController
) {
    HomeScreen(homeViewModel = homeViewModel,
        onLogOut = { loginViewModel.logOut(navigate_to_login) },
        navigateToDetails = { id: String, name: String ->
            navController.navigate(
                route = "${MovieDetailsDestination.route}/$id/${name.trim().replace(" ", "-")}",
            ) { launchSingleTop = true }
        },
        navigateToMore = { category: MediaCategories ->
            navController.navigate(
                route = "${MoreMovieDestination.route}/${category.title}",
            )
        })

}