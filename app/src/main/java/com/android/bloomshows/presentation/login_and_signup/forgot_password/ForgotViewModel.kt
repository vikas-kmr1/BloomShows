package com.android.bloomshows.presentation.login_and_signup.forgot_password

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

sealed interface ForgotUIState {
    object Success : ForgotUIState
    data class Error(val errorResponse: BloomShowsErrorResponse) : ForgotUIState
    data class Loading(val isLoading: Boolean) : ForgotUIState
}

@HiltViewModel
class ForgotViewModel @Inject constructor(
    private val accountService: AccountService,
) :
    ViewModel() {
    var forgotUiState: ForgotUIState by mutableStateOf(ForgotUIState.Loading(false))
        private set

    fun sendResetCredentialMail(email: String) {
        forgotUiState = ForgotUIState.Loading(true)
        viewModelScope.launch {
            forgotUiState = try {
                accountService.sendRecoveryEmail(email = email)
                ForgotUIState.Success
            } catch (firebaseError: FirebaseException) {
                ForgotUIState .Error(
                    BloomShowsErrorResponse(
                        message = firebaseError.localizedMessage,
                        errorCode = firebaseError.cause?.message + ":"
                    )
                )
            } catch (e: HttpException) {
                ForgotUIState .Error(BloomShowsErrorResponse("UNKOWN", "Network Error"))
            } catch (e: ApiException) {
                ForgotUIState .Error(BloomShowsErrorResponse("404", "Something wentwrong. Try later!"))
            } catch (e: Exception) {
                ForgotUIState .Error(
                    BloomShowsErrorResponse(
                        "System Error",
                        "Something went wrong. Try again!"
                    )
                )
            }
        }
    }

    fun resetState(){
        forgotUiState = ForgotUIState.Loading(false)
    }

}

