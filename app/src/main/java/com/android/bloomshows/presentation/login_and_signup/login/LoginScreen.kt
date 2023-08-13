package com.android.bloomshows.presentation.login_and_signup.login

import EmailState
import EmailStateSaver
import HyperlinkText
import PasswordState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.bloomshows.R
import com.android.bloomshows.ui.components.BloomshowsBranding
import com.android.bloomshows.ui.components.Email
import com.android.bloomshows.ui.components.ErrorSnackbar
import com.android.bloomshows.ui.components.Password
import com.android.bloomshows.ui.theme.BloomShowsTheme
import com.android.bloomshows.ui.theme.ExtraSmallElevation
import com.android.bloomshows.ui.theme.ExtraSmallPadding
import com.android.bloomshows.ui.theme.MediumPadding
import com.android.bloomshows.ui.theme.MediumTextSize
import com.android.bloomshows.ui.theme.SemiLargeTextSize
import com.android.bloomshows.ui.theme.SemiMediumTextSize
import com.android.bloomshows.ui.theme.SmallPadding
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    email: String?,
    onLogInSubmitted: (email: String, password: String) -> Unit,
    navToSignup: () -> Unit = {},
    navToForgot: () -> Unit = {},
    ) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val snackbarErrorText = "message"
    val snackbarActionLabel = "dimiss"

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
            modifier = Modifier,
            text = stringResource(R.string.welcome_to_bloomshows),
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = SemiLargeTextSize
            ),
        )

        //Google and facebook logins
        LoginWithGroup()

        Text(
            text = stringResource(R.string.or_continue_with_email),
            color = Color.Gray,
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Normal)
        )

        //email and password fields
        LoginInputFields(email = email, onLogInSubmitted = onLogInSubmitted)

        TextButton(
            //TODO handle snack bar snackBar accordingly
            onClick = {
                      navToForgot()
//                scope.launch {
//                    snackbarHostState.showSnackbar(
//                        message = snackbarErrorText,
//                        actionLabel = snackbarActionLabel
//                    )
//                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small,
        ) {
            Text(
                text = stringResource(R.string.forgot_password),
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = MediumTextSize,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
        }

        Text(
            text = stringResource(R.string.or),
            color = Color.Gray,
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Normal)
        )

        //Spacer(modifier = Modifier.height(16.dp))
        //TODO nav to signup
        OutlinedButton(
            onClick = { navToSignup() },
            modifier = Modifier
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.small,

            )
        {
            Text(
                modifier = Modifier,
                textAlign = TextAlign.Center,
                text = stringResource(R.string.signup),
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = MediumTextSize,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
        }

        PolicyAndTerms()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        ErrorSnackbar(
            snackbarHostState = snackbarHostState,
            onDismiss = { snackbarHostState.currentSnackbarData?.dismiss() },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}


@Composable
private fun LoginWithGroup(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(SmallPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Google login
        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(ExtraSmallElevation),
            shape = MaterialTheme.shapes.small,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .clickable(onClick = {})
                    .align(Alignment.CenterHorizontally).padding(SmallPadding),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.padding(end = ExtraSmallPadding),
                    painter = painterResource(R.drawable.logo_google),
                    contentScale = ContentScale.Crop,
                    contentDescription = stringResource(R.string.login_with_google)
                )
                Text(
                    text = stringResource(R.string.login_with_google),
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                )
            }
        }

        //FaceBook login
        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(ExtraSmallElevation),
            shape = MaterialTheme.shapes.small,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .clickable(onClick = {})
                    .align(Alignment.CenterHorizontally).padding(SmallPadding),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    modifier = Modifier.padding(end = ExtraSmallPadding),
                    painter = painterResource(R.drawable.logo_facebook),
                    contentDescription = stringResource(R.string.login_with_facebook),
                )
                Text(
                    text = stringResource(R.string.login_with_facebook),
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = MediumTextSize
                    )
                )
            }
        }
    }
}

@Composable
private fun LoginInputFields(
    email: String?,
    onLogInSubmitted: (email: String, password: String) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        val focusRequester = remember { FocusRequester() }
        val emailState by rememberSaveable(stateSaver = EmailStateSaver) {
            mutableStateOf(EmailState(email))
        }
        Email(emailState, onImeAction = { focusRequester.requestFocus() })

        //Spacer(modifier = Modifier.height(16.dp))

        val passwordState = remember { PasswordState() }

        //TODO authentication and user -credential verification
        val onSubmit = {
            if (emailState.isValid && passwordState.isValid) {
                onLogInSubmitted(emailState.text, passwordState.text)
            }
        }
        Password(
            label = stringResource(R.string.password),
            passwordState = passwordState,
            modifier = Modifier.focusRequester(focusRequester),
            onImeAction = { onSubmit() }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { onSubmit() },
            modifier = Modifier
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.small,
            enabled = emailState.isValid && passwordState.isValid
        )
        {
            Text(
                modifier = Modifier,
                textAlign = TextAlign.Center,
                text = stringResource(R.string.continue_label),
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = MediumTextSize,
                    color = if (emailState.isValid && passwordState.isValid) MaterialTheme.colorScheme.onBackground
                    else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f)
                )
            )
        }

    }
}


@Composable
private fun PolicyAndTerms() {

        HyperlinkText(
            fullText = stringResource(R.string.by_signing_up_you_agree_tour_terms_of_service_and_privacy_policy),
            hyperLinks = mutableMapOf(
                "Terms of service" to "https://google.com",
                "Privacy Policy" to "https://google.com"
            ),
            linkTextColor = MaterialTheme.colorScheme.primary,
            fontSize = SemiMediumTextSize
        )

}


@Preview
@Composable
fun PreviewLoginScreen() {

    BloomShowsTheme {
        LoginScreen(
            email = null,
            onLogInSubmitted = { _, _ -> },
            navToForgot = {  }
        )
    }
}

