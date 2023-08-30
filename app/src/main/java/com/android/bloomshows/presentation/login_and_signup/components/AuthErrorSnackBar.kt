package com.android.bloomshows.presentation.login_and_signup.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.android.bloomshows.ui.common_components.ErrorSnackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AuthErrorSnackBar(
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    message: String
) {
    LaunchedEffect(key1 = message) {
        scope.launch {
            snackbarHostState.showSnackbar(
                message = message,
                actionLabel = "Auth Failed!",
                duration = SnackbarDuration.Long
            )
        }
    }
}

@Composable
fun ShowSnackBar(
    color: Color = MaterialTheme.colorScheme.inverseSurface,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        ErrorSnackbar(
            snackbarHostState = snackbarHostState,
            onDismiss = { snackbarHostState.currentSnackbarData?.dismiss() },
            modifier = Modifier.align(Alignment.BottomCenter)
                .windowInsetsPadding(WindowInsets.navigationBars),
            color = color
        )
    }
}