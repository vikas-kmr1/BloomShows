package com.android.bloomshows.presentation.login_and_signup.login

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.bloomshows.network.model.BloomShowsErrorResponse
import com.android.bloomshows.network.services.auth.AccountService
import com.android.bloomshows.network.services.auth.SignInResult
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import kotlin.math.sign

sealed interface LoginUIState {
    data class LoginSuccess(
        val email: String = "",
        val password: String = "",
    ) : LoginUIState

    data class Error(val errorResponse: BloomShowsErrorResponse) : LoginUIState
    data class Progress(val inProgress: Boolean) : LoginUIState
}


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val accountService: AccountService,
) :
    ViewModel() {
    var uiState: LoginUIState by mutableStateOf(LoginUIState.Progress(false))
        private set

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun onSignInResult(result: SignInResult) {
        _state.update { it.copy(
            isSignInSuccessful = result.data != null,
            signInError = result.errorMessage
        ) }
    }

    fun resetState() {
        _state.update { SignInState() }
    }
    fun logIn(
        email: String,
        password: String,
        onLoginCompleted: () -> Unit
    ) {
        viewModelScope.launch {
            LoginUIState.Progress(true)

            uiState = try {
                accountService.authenticate(email, password)
                LoginUIState.LoginSuccess(email, password).apply {
                    onLoginCompleted()
                }
            } catch (e: IOException) {
                LoginUIState.Error(BloomShowsErrorResponse(404, ""))
            } catch (e: HttpException) {
                LoginUIState.Error(BloomShowsErrorResponse(404, ""))
            }
        }
    }



//    fun launchGoogleSignInIntent(result:ActivityResult){
//        Log.v("button-data",result.data.toString())
//
//                    viewModelScope.launch {
//                        val signInResult = accountService.signInWithIntent(
//                            intent = result.data ?: return@launch
//                        )
//                        onSignInResult(signInResult)
//                    }
//
//
//    }

//    private fun signInWithGoogle() {
//        viewModelScope.launch {
//        if(state.value.isSignInSuccessful)
//            accountService.signInWithGoogle()
//        }
//    }


    companion object {
        const val RC_SIGN_IN = 9001 // Request code for Google Sign-In
    }
}

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)
class SignInViewModel: ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun onSignInResult(result: SignInResult) {
        _state.update { it.copy(
            isSignInSuccessful = result.data != null,
            signInError = result.errorMessage
        ) }
    }

    fun resetState() {
        _state.update { SignInState() }
    }
}