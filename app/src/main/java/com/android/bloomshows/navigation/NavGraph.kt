package com.android.bloomshows.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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


@Composable
fun NavGraph(
    navigationController: NavHostController = rememberNavController()
) {
    NavHost(
        //TODO change this with the screen you are working on
        // be sure to reset it to splashscreen when you donw
        modifier = Modifier,
        startDestination = OnboardingDestination.route,
        navController = navigationController,
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
               // navigationController.popBackStack()
                navigationController.navigate(LoginDestination.route)
            })
        }

        composable(route = LoginDestination.route) {
            EnterAnimation {
                LoginRoute(
                    onNavigateToSignUp = {
                        navigationController.popBackStack()
                        navigationController.navigate(route = SignUpDestination.route)
                    },
                    onLogInSubmitted = {},
                    onNavigateToForgot = {
                        navigationController.navigate(
                            ForgetCredentialsDestination.route
                        )
                    }
                )
            }
        }

        composable(route = SignUpDestination.route) {
            EnterAnimation {
                SignUpRoute(
                    onNavigateToLogin = {
                        navigationController.popBackStack()
                        navigationController.navigate(route = LoginDestination.route)
                    },
                    onSignUpSubmitted = {},
                )
            }
        }
        composable(route = ForgetCredentialsDestination.route) {
            EnterAnimation {
                Forgot_password_route(
                    onNavigateUp = { navigationController.navigateUp() }
                )
            }
        }
        composable(route = HomeDestination.route) {
                EnterAnimation {
                HomeRoute()
                }

        }

    }

}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun EnterAnimation(content: @Composable () -> Unit) {
    AnimatedVisibility(
        visible = true,
        enter = slideInVertically(
            initialOffsetY = { -100 }
        ) + expandVertically(
            expandFrom = Alignment.Top
        ) + fadeIn(initialAlpha = 0.3f),
        exit = slideOutVertically() + shrinkVertically() + fadeOut(),
        content = content,
        initiallyVisible = false
    )

}