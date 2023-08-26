package com.android.bloomshows.presentation.login_and_signup.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.bloomshows.network.model.BloomShowsErrorResponse
import com.android.bloomshows.network.services.auth.AccountService
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


sealed interface SignUpUIState {
    data class SignUpSuccess(
        val email: String = "",
        val password: String = "",
        val username: String = ""
    ) : SignUpUIState

    data class Error(val errorResponse: BloomShowsErrorResponse) : SignUpUIState
    data class Progress(val inProgress: Boolean) : SignUpUIState
}


@HiltViewModel
class SignUpViewModel @Inject constructor(private val accountService: AccountService) :
    ViewModel() {
    var uiState: SignUpUIState by mutableStateOf(SignUpUIState.Progress(false))
        private set


    fun signUp(
        email: String ,
        password: String ,
        username: String,
        onSignUpCompleted: () -> Unit
    ) {
        viewModelScope.launch {
            SignUpUIState.Progress(true)

            uiState = try {
                accountService.createAccount(email, password)
                SignUpUIState.SignUpSuccess(email, password, username)
            } catch (e: IOException) {
                SignUpUIState.Error(BloomShowsErrorResponse(404, ""))
            } catch (e: HttpException) {
                SignUpUIState.Error(BloomShowsErrorResponse(404, ""))
            }
        }
    }
}