package com.android.bloomshows.ui.common_components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.android.bloomshows.R
import com.android.bloomshows.ui.theme.ExtraSmallPadding


@Composable
fun ErrorSnackbar(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier= Modifier,
    onDismiss: () -> Unit = { },
    color:Color
) {
    SnackbarHost(
        hostState = snackbarHostState,
        snackbar = { data ->
            Snackbar(
                modifier = Modifier.padding(ExtraSmallPadding),
                content = {
                    Text(
                        text = data.visuals.message,
                        textAlign = TextAlign.Justify,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                },
                containerColor = color,
                dismissAction = {
                    data.visuals.actionLabel?.let {
                        IconButton(onClick = onDismiss) {
                            Icon(
                                imageVector = Icons.Filled.Cancel,
                                contentDescription = stringResource(R.string.dismiss),
                            )
                        }
                    }
                }
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(Alignment.Bottom)
    )
}

