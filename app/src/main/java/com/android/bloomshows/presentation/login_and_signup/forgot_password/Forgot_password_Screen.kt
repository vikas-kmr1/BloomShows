package com.android.bloomshows.presentation.login_and_signup.forgot_password

import com.android.bloomshows.presentation.login_and_signup.utils.EmailState
import com.android.bloomshows.presentation.login_and_signup.utils.EmailStateSaver
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.bloomshows.R
import com.android.bloomshows.presentation.login_and_signup.components.LoginSignupTextField
import com.android.bloomshows.ui.common_components.BloomshowsBranding
import com.android.bloomshows.ui.theme.BloomShowsTheme
import com.android.bloomshows.ui.theme.MediumPadding
import com.android.bloomshows.ui.theme.MediumTextSize
import com.android.bloomshows.ui.theme.SemiLargeTextSize
import com.android.bloomshows.ui.theme.SmallPadding

@Composable
fun Forgot_password_Screen(
    onSubmitted: (email: String) -> Unit,
    navigateUp: () -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
            .padding(horizontal = MediumPadding)
            .padding(top = MediumPadding)
            .scrollable(orientation = Orientation.Vertical, state = rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(SmallPadding, Alignment.Top)
    ) {
        BloomshowsBranding(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center)
        )

        Text(
            modifier = Modifier.padding(bottom = MediumPadding),
            text = stringResource(R.string.welcome_to_bloomshows),
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = SemiLargeTextSize
            ),
        )

        Text(
            modifier = Modifier.padding(bottom = MediumPadding),
            text = stringResource(R.string.forgot_password),
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = SemiLargeTextSize
            ),
        )
        Text(
            text = stringResource(R.string.enter_your_email_to_reset_password),
            color = Color.Gray,
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Normal)
        )

        Image(modifier = Modifier.fillMaxWidth(),painter = painterResource(R.drawable.mail_service), contentDescription = null)

        ForgotInputFields(onSubmitted = onSubmitted)
        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {navigateUp()},
            shape = MaterialTheme.shapes.small,
        ) {

            Text(
                modifier = Modifier,
                textAlign = TextAlign.Center,
                text = stringResource(R.string.cancel),
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = MediumTextSize,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
        }

    }
}

@Composable
private fun ForgotInputFields(
    onSubmitted: (email: String) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val focusRequester = remember { FocusRequester() }
        val emailState by rememberSaveable(stateSaver = EmailStateSaver) {
            mutableStateOf(EmailState(""))
        }
        LoginSignupTextField(emailState, onImeAction = { focusRequester.requestFocus() })

        //TODO authentication and user -credential verification
        val onSubmit = {
            if (emailState.isValid) {
                onSubmitted(emailState.text)
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = { onSubmit() },
            modifier = Modifier
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.small,
            enabled = emailState.isValid
        )
        {
            Text(
                modifier = Modifier,
                textAlign = TextAlign.Center,
                text = stringResource(R.string.continue_label),
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = MediumTextSize,
                    color = if (emailState.isValid) MaterialTheme.colorScheme.onBackground
                    else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f)
                )
            )
        }

    }
}

@Preview
@Composable
fun Preview_Forgot_password_Screen() {
    BloomShowsTheme {
        Forgot_password_Screen(
            onSubmitted = { _ -> },
            navigateUp = {}
        )
    }
}