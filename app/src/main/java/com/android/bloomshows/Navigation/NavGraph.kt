package com.android.bloomshows.Navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.android.bloomshows.presentation.on_boarding.OnBoardingScreen
import com.android.bloomshows.presentation.splash.SplashScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(
    navigationController: NavHostController = rememberNavController()
) {

    NavHost(
        startDestination = SplashDestination.route,
        navController = navigationController
    ) {
        composable(route = SplashDestination.route) {
            SplashScreen(
                navigate_to_home = {
                    navigationController.navigate(OnboardingDestination.route)
                },
                navigate_to_onboarding = {
                    navigationController.popBackStack()
                    navigationController.navigate(OnboardingDestination.route)
                }
            )

        }

        composable(route = OnboardingDestination.route) {
            OnBoardingScreen()
        }

    }

}