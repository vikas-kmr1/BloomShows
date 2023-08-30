package com.android.bloomshows.presentation.login_and_signup.login

import android.accounts.NetworkErrorException
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.bloomshows.network.model.BloomShowsErrorResponse
import com.android.bloomshows.network.model.SignInResult
import com.android.bloomshows.network.services.auth.AccountService
import com.android.bloomshows.network.services.auth.GoogleAuthUiClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject


sealed interface LoginUIState {
    data class LoginSuccess(
        val isSignInSuccessful: Boolean = false
    ) : LoginUIState

    data class Error(val errorResponse: BloomShowsErrorResponse) : LoginUIState
    data class Progress(val inProgress: Boolean) : LoginUIState
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val accountService: AccountService,
) :
    ViewModel() {
    var logInUiState: LoginUIState by mutableStateOf(LoginUIState.Progress(false))
        private set


    val currentUser = accountService.currentUser.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = null
    ) //userdata
    val hasuser: Boolean = accountService.hasUser
    val userId = accountService.currentUserId


    @Inject
    lateinit var googleAuthUiClient: GoogleAuthUiClient

    fun resetState() {
        logInUiState = LoginUIState.Progress(false)
    }

    fun onGoogleSigninResult(result: SignInResult) {
        logInUiState = if (result.errorMessage == null) LoginUIState.LoginSuccess(true)
        else LoginUIState.Error(
            BloomShowsErrorResponse(
                message = result.errorMessage.message,
                errorCode = ""
            )
        )
    }

    fun logIn(
        email: String,
        password: String,
    ) {
        viewModelScope.launch {
            logInUiState = LoginUIState.Progress(true)
            logInUiState = try {
                accountService.authenticate(email, password)
                LoginUIState.LoginSuccess(true)
            } catch (firebaseError: FirebaseAuthException) {
                LoginUIState.Error(
                    BloomShowsErrorResponse(
                        message = firebaseError.localizedMessage,
                        errorCode = firebaseError.errorCode
                    )
                )
            } catch (firebaseError: FirebaseTooManyRequestsException) {
                LoginUIState.Error(
                    BloomShowsErrorResponse(
                        message = firebaseError.message,
                        errorCode = firebaseError.cause.toString()
                    )
                )
            } catch (firebaseError: FirebaseNetworkException) {
                LoginUIState.Error(
                    BloomShowsErrorResponse(
                        message = firebaseError.message,
                        errorCode ="NETWORK_ERROR"
                    )
                )
            } catch (e: HttpException) {
                LoginUIState.Error(BloomShowsErrorResponse("UNKOWN", "Network Error"))
            } catch (e: ApiException) {
                LoginUIState.Error(BloomShowsErrorResponse(404, "Something wentwrong. Try later!"))
            }
        }
    }

    fun logOut(navigate_to_login: () -> Unit) {
        viewModelScope.launch {
            accountService.signOut()
            googleAuthUiClient.signOut().also {
                resetState()
                navigate_to_login()
            }
        }
    }

    fun logInWithGoogle(launcher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>) {
        viewModelScope.launch {
            logInUiState = LoginUIState.Progress(true)
            logInUiState = try {
                val signInIntentSender = googleAuthUiClient.signIn()
                launcher.launch(
                    IntentSenderRequest.Builder(
                        signInIntentSender ?: return@launch
                    ).build()
                )
                LoginUIState.Progress(true)
            } catch (firebaseError: FirebaseAuthException) {
                LoginUIState.Error(
                    BloomShowsErrorResponse(
                        message = firebaseError.localizedMessage,
                        errorCode = firebaseError.errorCode
                    )
                )
            } catch (firebaseError: FirebaseTooManyRequestsException) {
                LoginUIState.Error(
                    BloomShowsErrorResponse(
                        message = firebaseError.message,
                        errorCode = firebaseError.cause.toString()
                    )
                )
            } catch (firebaseError: FirebaseNetworkException) {
                LoginUIState.Error(
                    BloomShowsErrorResponse(
                        message = firebaseError.message,
                        errorCode = "NETWORK_ERROR"
                    )
                )
            } catch (e: HttpException) {
                LoginUIState.Error(BloomShowsErrorResponse("UNKNOWN", "Network Error"))
            } catch (e: ApiException) {
                LoginUIState.Error(BloomShowsErrorResponse(404, "Something wentwrong. Try later!"))
            }
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}


