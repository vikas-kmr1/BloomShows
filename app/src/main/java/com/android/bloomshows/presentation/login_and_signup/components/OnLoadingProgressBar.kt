package com.android.bloomshows.presentation.login_and_signup.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.zIndex
import com.android.bloomshows.utils.extension_fun.gesturesEnabled

@Composable
fun OnLoadingProgressBar() {
    Column(
        modifier = Modifier.fillMaxSize().gesturesEnabled(false).zIndex(100f)
            .background(
            MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f)
        ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        CircularProgressIndicator()
    }
}
