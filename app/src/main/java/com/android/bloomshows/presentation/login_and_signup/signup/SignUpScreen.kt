package com.android.bloomshows.presentation.login_and_signup.signup

import HyperlinkText
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.bloomshows.R
import com.android.bloomshows.presentation.login_and_signup.components.LoginSignupTextField
import com.android.bloomshows.presentation.login_and_signup.components.Password
import com.android.bloomshows.presentation.login_and_signup.utils.ConfirmPasswordState
import com.android.bloomshows.presentation.login_and_signup.utils.EmailState
import com.android.bloomshows.presentation.login_and_signup.utils.EmailStateSaver
import com.android.bloomshows.presentation.login_and_signup.utils.NameState
import com.android.bloomshows.presentation.login_and_signup.utils.NameStateSaver
import com.android.bloomshows.presentation.login_and_signup.utils.PasswordState
import com.android.bloomshows.ui.common_components.BloomshowsBranding
import com.android.bloomshows.ui.common_components.ErrorSnackbar
import com.android.bloomshows.ui.theme.BloomShowsTheme
import com.android.bloomshows.ui.theme.MediumPadding
import com.android.bloomshows.ui.theme.MediumTextSize
import com.android.bloomshows.ui.theme.SemiLargeTextSize
import com.android.bloomshows.ui.theme.SemiMediumIcon
import com.android.bloomshows.ui.theme.SemiMediumTextSize
import com.android.bloomshows.ui.theme.SmallPadding
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(

    onSignUpSubmitted: (name: String, email: String, password: String) -> Unit,
    navToLogin: () -> Unit = {},

    ) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val snackbarErrorText = "message"
    val snackbarActionLabel = "dimiss"
    Column(
        modifier = Modifier.fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
            .verticalScroll(state = rememberScrollState())
            .padding(horizontal = MediumPadding)
            .padding(top = MediumPadding),
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

        //name, email and password fields
        SignUpInputFields(
            onSignUpSubmitted = onSignUpSubmitted,
            showSnackBar = { message ->
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = message,
                        actionLabel = snackbarActionLabel,
                        duration = SnackbarDuration.Short
                    )

                }
            })


        //Spacer(modifier = Modifier.height(16.dp))
        //TODO nav to signup
        OutlinedButton(
            onClick = { navToLogin() },
            modifier = Modifier
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.small,

            )
        {
            Text(
                modifier = Modifier,
                textAlign = TextAlign.Center,
                text = stringResource(R.string.login),
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = MediumTextSize,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
        }

    }

    Box(modifier = Modifier.fillMaxSize()) {
        ErrorSnackbar(
            snackbarHostState = snackbarHostState,
            onDismiss = { snackbarHostState.currentSnackbarData?.dismiss() },
            modifier = Modifier.align(Alignment.BottomCenter).windowInsetsPadding(WindowInsets.navigationBars)
        )
    }
}


@Composable
private fun SignUpInputFields(
    onSignUpSubmitted: (name: String, email: String, password: String) -> Unit,
    showSnackBar: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        //TODO handle name ,email,password states
        val focusRequester = remember { FocusRequester() }
        val emailState by rememberSaveable(stateSaver = EmailStateSaver) {
            mutableStateOf(EmailState())
        }
        val nameState by rememberSaveable(stateSaver = NameStateSaver) {
            mutableStateOf(NameState())
        }

        //Name field
        LoginSignupTextField(
            label = stringResource(R.string.name),
            state = nameState,
            onImeAction = { focusRequester.requestFocus() })
        //email filed
        LoginSignupTextField(state = emailState, onImeAction = { focusRequester.requestFocus() })


        val passwordState = remember { PasswordState() }
        val passwordConfirmState = remember { ConfirmPasswordState(passwordState) }

        val (termsCheckedState, onStateChange) = remember { mutableStateOf(false) }

        //TODO authentication and user -credential verification
        val onSubmit = {
            if (emailState.isValid && passwordState.isValid && passwordConfirmState.isValid && nameState.isValid && termsCheckedState) {
                onSignUpSubmitted(nameState.text, emailState.text, passwordState.text)
            }
        }
        Password(
            label = stringResource(R.string.password),
            passwordState = passwordState,
            modifier = Modifier.focusRequester(focusRequester),
            onImeAction = { onSubmit() }
        )
        //Confirm password
        Password(
            label = stringResource(R.string.confirm_password),
            passwordState = passwordConfirmState,
            modifier = Modifier.focusRequester(focusRequester),
            onImeAction = { onSubmit() }
        )

        //validating password pattern
        Spacer(modifier = Modifier.height(SmallPadding))

        val validConditions = listOf(
            passwordState.isMinLength,
            passwordState.isContainsNumber,
            passwordState.isContainsSpecial,
            passwordState.isContainsUpper && passwordState.isContainsLower,
        )
        VALIDATE_PATTERN_CONDITIONS.forEachIndexed { index: Int, strResId: Int ->
            ValidatePattern(label = stringResource(strResId), index = index, validConditions)
        }


        Spacer(modifier = Modifier.height(SmallPadding))

        PolicyAndTerms(
            checkState = termsCheckedState,
            onStateChange = onStateChange
        )

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (!termsCheckedState) {
                    showSnackBar("Accept Terms and conditions")
                }
                onSubmit()
            },
            modifier = Modifier
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.small,
            enabled = emailState.isValid && passwordState.isValid && passwordConfirmState.isValid
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
private fun ValidatePattern(
    label: String,
    index: Int,
    validCondtions: List<Boolean>,
    icon: ImageVector = Icons.Filled.CheckCircle,
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            SmallPadding, Alignment.Start
        )
    ) {
        Icon(
            imageVector = icon,
            tint = if (validCondtions[index]) Color.Green else Color.Gray,
            modifier = Modifier.size(SemiMediumIcon),
            contentDescription = label + "icon"
        )
        Text(
            text = label, color = Color.Gray,
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize = SemiMediumTextSize,
                fontWeight = FontWeight.Normal
            )
        )
    }
}

@Composable
private fun PolicyAndTerms(checkState: Boolean, onStateChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(SmallPadding, Alignment.Start),
    ) {
        Checkbox(
            modifier = Modifier.size(SemiMediumIcon),
            checked = checkState,
            onCheckedChange = { onStateChange(!checkState) }
        )

        HyperlinkText(
            fullText = stringResource(R.string.agreeging_to_the_terms_of_service_and_privacy_policy_with_the_provider),
            hyperLinks = mutableMapOf(
                "Terms of service" to "https://google.com",
                "Privacy Policy" to "https://google.com"
            ),
            linkTextColor = MaterialTheme.colorScheme.primary,
            fontSize = SemiMediumTextSize,
            textStyle = MaterialTheme.typography.labelSmall.copy(
                textAlign = TextAlign.Justify,
                fontWeight = FontWeight.Normal,
                color = Color.Gray
            )
        )

    }

}

private val VALIDATE_PATTERN_CONDITIONS = listOf(
    R.string.at_least_8_characters,
    R.string.at_least_1_number,
    R.string.atleast_one_special_character,
    R.string.both_both_upper_and_lower_case_letters,
)


@Preview
@Composable
fun PreviewSignUpScreen() {

    BloomShowsTheme {
        SignUpScreen(
            onSignUpSubmitted = { _, _, _ -> },
        )
    }
}

