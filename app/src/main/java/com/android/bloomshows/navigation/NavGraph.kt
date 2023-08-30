package com.android.bloomshows.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.bloomshows.presentation.home.HomeRoute
import com.android.bloomshows.presentation.login_and_signup.forgot_password.Forgot_password_route
import com.android.bloomshows.presentation.login_and_signup.login.LoginRoute
import com.android.bloomshows.presentation.login_and_signup.signup.SignUpRoute
import com.android.bloomshows.presentation.on_boarding.OnBoardingScreen
import com.android.bloomshows.presentation.splash.SplashScreen
import com.android.bloomshows.utils.animations.EnterExitFadeTransition
import com.android.bloomshows.utils.animations.EnterFallDownAnimation


@Composable
fun NavGraph(
    navigationController: NavHostController = rememberNavController(),
) {
    NavHost(
        //TODO change this with the screen you are working on
        // be sure to reset it to splashscreen when you donw
        modifier = Modifier,
        startDestination = SplashDestination.route,
        navController = navigationController,
    ) {
        composable(route = SplashDestination.route) {
            SplashScreen(
                navigate_to_home = {
                    navigationController.navigateSingleTopTo(HomeDestination.route)
                },
                navigate_to_onboarding = {
                    navigationController.navigateSingleTopTo(OnboardingDestination.route)
                },
                navigate_to_login = { navigationController.navigateSingleTopTo(LoginDestination.route) }
            )

        }
        composable(route = OnboardingDestination.route) {
            OnBoardingScreen(navigate_to_login = {
                navigationController.navigateSingleTopTo(LoginDestination.route)
            })
        }

        composable(route = LoginDestination.route) {
            EnterFallDownAnimation {
                LoginRoute(
                    navigateToSignUp = {
                        navigationController.navigate(route = SignUpDestination.route)
                    },
                    navigateToForgot = {
                        navigationController.navigate(ForgetCredentialsDestination.route)
                    },
                    navigateToHome = {
                        navigationController.navigateSingleTopTo(route = HomeDestination.route)
                    }
                )
            }
        }

        composable(route = SignUpDestination.route) {
            EnterFallDownAnimation {
                SignUpRoute(
                    onNavigateToLogin = {
                        navigationController.navigateUp()
                    },
                    onSignUpSubmitted = {
                        navigationController.navigateSingleTopTo(route = HomeDestination.route)
                    }
                )
            }
        }
        composable(route = ForgetCredentialsDestination.route) {
            EnterFallDownAnimation {
                Forgot_password_route(
                    onNavigateUp = { navigationController.navigateUp() }
                )
            }
        }
        composable(route = HomeDestination.route) {
            EnterExitFadeTransition {
                HomeRoute(
                    navigate_to_login = {
                        navigationController.navigateSingleTopTo(LoginDestination.route)
                    }
                )
            }
        }
    }
}


fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(
            // this@navigateSingleTopTo.graph.findStartDestination().id
            0
        ) {
            saveState = true
            inclusive = true
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = false
    }
