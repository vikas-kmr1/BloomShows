package com.android.bloomshows.network.model

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
    val errorMessage: Exception?
)