package com.android.bloomshows.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.android.bloomshows.R

// Set of Material typography styles to start with
val handlee = FontFamily(
    Font(R.font.handlee, weight = FontWeight.SemiBold)
)

val cera_pro = FontFamily(
    Font(R.font.cera_pro_light, weight = FontWeight.Normal),
    Font(R.font.cera_pro_bold, weight = FontWeight.Bold),
    Font(R.font.cera_pro_medium, weight = FontWeight.Medium),
    Font(R.font.cera_pro_black, weight = FontWeight.Black),
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    ,
    labelLarge = TextStyle(
        fontFamily = cera_pro,
        fontWeight = FontWeight.Black,
        fontSize = 42.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp
    ),
    labelSmall  = TextStyle(
        fontFamily = cera_pro,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp
    ),
    titleSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )

)