package com.android.bloomshows.network.model

data class User(
    val userId: String = "",
    val isAnonymous: Boolean = false,
    val username:String? = "",
    val profilePic:String? = "",
    val email:String = "",
    val emailVerfied:Boolean = false,
    val createdAT:Long? = 0
)

data class SignInResult(
    val data: User?,
    val errorMessage: Exception?
)