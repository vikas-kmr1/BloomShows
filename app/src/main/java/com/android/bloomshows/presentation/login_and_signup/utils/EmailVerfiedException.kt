package com.android.bloomshows.presentation.login_and_signup.utils

import com.google.firebase.FirebaseException

class EmailVerfiedException(message: String,errorcode:Throwable): FirebaseException(message,errorcode)