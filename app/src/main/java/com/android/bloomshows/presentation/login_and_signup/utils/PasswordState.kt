package com.android.bloomshows.presentation.login_and_signup.utils

import java.util.regex.Pattern

private val PAASSWORD_VALIDATION_REGEX =
    "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}$"

class PasswordState :
    TextFieldState(validator = ::isPasswordValid, errorFor = ::passwordValidationError){

    companion object {
        private const val MIN_LENGTH = 8
        private const val LOWER_CASE_REGEX = ".*[a-z].*"
        private const val UPPER_CASE_REGEX = ".*[A-Z].*"
        private const val DIGIT_REGEX = ".*[0-9].*"
        private const val SPECIAL_CASE_REGEX = ".*[@\$!%*?&].*"
    }

    val isContainsLower: Boolean
        get() = Pattern.matches(LOWER_CASE_REGEX, text)

    val isContainsUpper: Boolean
        get() = Pattern.matches(UPPER_CASE_REGEX, text)

    val isContainsNumber: Boolean
        get() = Pattern.matches(DIGIT_REGEX, text)

    val isContainsSpecial: Boolean
        get() = Pattern.matches(SPECIAL_CASE_REGEX, text)

    val isMinLength: Boolean
        get() = text.length >= MIN_LENGTH


}

class ConfirmPasswordState(private val passwordState: PasswordState) : TextFieldState() {
    override val isValid
        get() = passwordAndConfirmationValid(passwordState.text, text)

    override fun getError(): String? {
        return if (showErrors()) {
            passwordConfirmationError()
        } else {
            null
        }
    }
}

private fun isPasswordValid(password: String): Boolean {
    return Pattern.matches(PAASSWORD_VALIDATION_REGEX, password)
}

@Suppress("UNUSED_PARAMETER")
private fun passwordValidationError(password: String): String {
    return "Invalid password"
}

private fun passwordConfirmationError(): String {
    return "Passwords don't match"
}

private fun passwordAndConfirmationValid(password: String, confirmedPassword: String): Boolean {
    return isPasswordValid(password) && password == confirmedPassword
}

private const val MIN_LENGTH = 8

private const val LOWER_CASE_REGEX = "(?=.*[a-z])"
private const val UPPER_CASE_REGEX = "(?=.*[A-Z])"
private const val DIGIT_REGEX = "(?=.*\\d)"
private const val SPECIAL_CASE_REGEX = "(?=.*[@\$!%*?&])"
private const val MIN_LENGHT_REGEX = "[A-Za-z\\d@\$!%*?&]{8,}"
