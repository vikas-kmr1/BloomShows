package com.android.bloomshows.ui.common_components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    id: String = "" ,
    handleNavBack: () -> Unit = {},
    containerColor: Color = MaterialTheme.colorScheme.surface
) {

    TopAppBar(
        title = {
            AutoResizedText(text = title.capitalize(Locale.ROOT),)
        },
        navigationIcon = { BackNavButton(handleNavBack = handleNavBack) },
        modifier = modifier,
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = containerColor
        ),
    )
}

@Composable
fun BackNavButton(handleNavBack: () -> Unit) {
    IconButton(onClick = handleNavBack) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = "Back arrow"
        )
    }
}
