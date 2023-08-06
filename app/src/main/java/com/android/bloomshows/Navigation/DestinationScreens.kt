package com.android.bloomshows.Navigation

interface Screens{
    val title:String
    val route:String
}

internal object SplashDestination:Screens{
    override val title: String = "Splash"
    override val route: String = "SplashScreen"
}
internal object OnboardingDestination:Screens{
    override val title: String = "OnBoarding"
    override val route: String = "OnBoardingScreen"
}
internal object HomeDestination:Screens{
    override val title: String = "Home"
    override val route: String = "HomeScreen"
}