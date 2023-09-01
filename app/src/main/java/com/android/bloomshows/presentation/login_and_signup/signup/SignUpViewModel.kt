package com.android.bloomshows.presentation.login_and_signup.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.bloomshows.network.model.BloomShowsErrorResponse
import com.android.bloomshows.network.services.auth.AccountService
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject


sealed interface SignUpUIState {
    data class SignUpSuccess(val isSignInSuccessful: Boolean = false) : SignUpUIState
    data class Error(val errorResponse: BloomShowsErrorResponse) : SignUpUIState
    data class Progress(val inProgress: Boolean) : SignUpUIState
}

@HiltViewModel
class SignUpViewModel @Inject constructor(private val accountService: AccountService) :
    ViewModel() {
    var signUpUiState: SignUpUIState by mutableStateOf(SignUpUIState.Progress(false))
        private set

    fun signUp(
        email: String,
        password: String,
        username: String,
    ) {
        viewModelScope.launch {
            signUpUiState = SignUpUIState.Progress(true)
            signUpUiState = try {
                accountService.createAccount(username, email, password)
                SignUpUIState.SignUpSuccess(true)
            } catch (firebaseError: FirebaseException) {
                SignUpUIState.Error(
                    BloomShowsErrorResponse(
                        message = firebaseError.localizedMessage,
                        errorCode = firebaseError.cause?.message + ":"
                    )
                )
            } catch (e: HttpException) {
                SignUpUIState.Error(BloomShowsErrorResponse("UNKOWN", "Network Error"))
            } catch (e: ApiException) {
                SignUpUIState.Error(
                    BloomShowsErrorResponse(
                        "404",
                        "Something went wrong. Try later!"
                    )
                )

            }
        }
    }
}