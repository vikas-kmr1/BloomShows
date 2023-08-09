package com.android.bloomshows.Navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.android.bloomshows.presentation.login.LoginRoute
import com.android.bloomshows.presentation.on_boarding.OnBoardingScreen
import com.android.bloomshows.presentation.on_boarding.onBoardingSlides
import com.android.bloomshows.presentation.splash.SplashScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(
    navigationController: NavHostController = rememberNavController()
) {

    NavHost(
        //TODO change this with the screen you are working on
        // be sure to reset it to splashscreen when you donw
        startDestination = SplashDestination.route,
        navController = navigationController
    ) {
        composable(route = SplashDestination.route) {
            SplashScreen(
                navigate_to_home = {
                    navigationController.navigate(OnboardingDestination.route)
                },
                navigate_to_onboarding = {
                    navigationController.popBackStack()// prevent navigation to splash again  when onBack clicked
                    navigationController.navigate(OnboardingDestination.route)
                }
            )

        }

        composable(route = OnboardingDestination.route) {
            OnBoardingScreen(navigate_to_login = {
                //navigationController.popBackStack()
                navigationController.navigate(LoginDestination.route)
            })
        }

        composable(route = LoginDestination.route){
            LoginRoute()
        }
        composable(route = SignUpDestination.route){

        }
        composable(route = ForgetCredentialsDestination.route){

        }
        composable(route = HomeDestination.route){

        }

    }

}