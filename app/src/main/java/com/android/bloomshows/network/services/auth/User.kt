package com.android.bloomshows.network.services.auth

import android.service.autofill.UserData

data class User(
    val userId: String = "",
    val isAnonymous: Boolean = true,
    val username:String?="",
    val profilePic:String?=""
)
//data class User(
//    val userId: String = "",
//    val isAnonymous: Boolean = true,
//    val username:String? = "",
//    val profilePic:String? = ""
//)

data class SignInResult(
    val data: User?,
    val errorMessage: String?
)