package com.android.bloomshows.network.model

data class User(
    val userId: String = "",
    val isAnonymous: Boolean = true,
    val username:String?="",
    val profilePic:String?="",
    val email:String?="",
    val emailVerfied:Boolean = false
)

data class SignInResult(
    val data: User?,
    val errorMessage: Exception?
)