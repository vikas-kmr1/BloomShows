package com.android.bloomshows.presentation.login_and_signup.utils


class NameState(val name: String? = null) :
    TextFieldState(validator = ::isNameValid, errorFor = ::nameValidationError) {
    init {
        name?.let {
            text = it
        }
    }
}

/**
 * Returns an error to be displayed or null if no error was found
 */
private fun nameValidationError(name: String): String {
    return if(name.length > 50) "maximum length reached 50" else "name must not be empty"
}

private fun isNameValid(name : String): Boolean {
    return name.isNotBlank() && (name.length <= 50)
}

val NameStateSaver = textFieldStateSaver(NameState())
