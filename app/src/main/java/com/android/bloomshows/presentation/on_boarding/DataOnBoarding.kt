package com.android.bloomshows.presentation.on_boarding

import androidx.compose.ui.graphics.Color
import com.android.bloomshows.R
import com.android.bloomshows.ui.theme.onBoardCyan
import com.android.bloomshows.ui.theme.onBoardPink
import com.android.bloomshows.ui.theme.onBoardYellow

data class DataOnBoarding(
    val label: String,
    val subLabel: String,
    val slideind: Int,
    val illustration: Int,
    val backgroundColor:Color,
)
//TODO replace illustration according to slide
val onBoardingSlides = listOf(

    DataOnBoarding(
        label = "Welcome to BloomShows" ,
        subLabel =  "Step into a World of Cinematic Delights",
        slideind = 0,
        illustration = R.drawable.illus_booking,
        backgroundColor = onBoardYellow
    ),
    DataOnBoarding(
        label = "Discover Movies and Showtimes" ,
        subLabel =  "Explore, Choose, and Immerse Yourself in Movie Magic",
        slideind = 1,
        illustration = R.drawable.illus_booking,
        backgroundColor = onBoardCyan
    ),
    DataOnBoarding(
        label = "Easy Booking and Enjoyment" ,
        subLabel =  "Seamless Booking, Instant Joy – Your Ultimate Movie Experience Awaits",
        slideind = 2,
        illustration = R.drawable.illus_booking,
        backgroundColor = onBoardPink
    ),


)
