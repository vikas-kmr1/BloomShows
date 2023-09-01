package com.android.bloomshows.ui.common_components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.android.bloomshows.R

@Composable

fun NotifyAlertDialog(
    showDialog: Boolean = true,
    title: String = "Title",
    text: String = "text",
    icon: @Composable ()->Unit ,
    onConfirmClicked: () -> Unit,
    confirmBtnTxt: String = stringResource(R.string.confirm),
    dismissBtnTxt: String = stringResource(R.string.dismiss)
) {
    val openDialog = remember { mutableStateOf(showDialog) }
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back
                // button. If you want to disable that functionality, simply use an empty
                // onDismissRequest.
                openDialog.value = false
            },
            icon = icon,
            title = {
                Text(modifier = Modifier.fillMaxWidth(), text = title, textAlign = TextAlign.Center)
            },
            text = {
                Text(modifier = Modifier.fillMaxWidth(),text = text, textAlign = TextAlign.Center)
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        onConfirmClicked()
                    }
                ) {
                    Text(text = confirmBtnTxt, style = MaterialTheme.typography.labelSmall)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    }
                ) {
                    Text(text = dismissBtnTxt, style = MaterialTheme.typography.labelSmall)
                }
            }
        )
    }
}