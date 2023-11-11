package com.android.bloomshows.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.android.bloomshows.navigation.MovieDetailsDestination.id

interface Screens {
    val title: String
    val route: String
}

internal object SplashDestination : Screens {
    override val title: String = "Splash"
    override val route: String = "SplashScreen"
}

internal object OnboardingDestination : Screens {
    override val title: String = "OnBoarding"
    override val route: String = "OnBoardingScreen"
}

internal object LoginDestination : Screens {
    override val title: String = "Login"
    override val route: String = "LoginScreen"
}
internal object SignUpDestination : Screens {
    override val title: String = "SignUp"
    override val route: String = "SignupScreen"
}

internal object ForgetCredentialsDestination : Screens {
    override val title: String = "Forget Credentails"
    override val route: String = "ForgetCredentialsScreen"
}

internal object HomeDestination : Screens {
    override val title: String = "Home"
    override val route: String = "HomeScreen"
}
internal object MovieDetailsDestination : Screens {
    override val title: String = "Details"
    override val route: String = "DetailsScreen"
    val id:String = "id"
    val name:String = "name"
    val routeWithArgs = "$route/{$id}/{$name}"

    val arguments = listOf(
        navArgument(name = id) {
            type = NavType.StringType
            defaultValue = id
        },
        navArgument(name = name) {
            type = NavType.StringType
            defaultValue = name
        }
    )
}
internal object MoreMovieDestination : Screens {
    override val title: String = "More Movies"
    override val route: String = "movies"
    val routeWithArgs = "$route/{$title}"

    val arguments = listOf(
        navArgument(name = title ) {
            type = NavType.StringType
            defaultValue = title
        },
    )
}