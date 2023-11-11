package com.android.bloomshows.presentation.login_and_signup.login

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
import com.google.firebase.FirebaseException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber
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

    @Inject
    lateinit var googleAuthUiClient: GoogleAuthUiClient


    fun resetState() {
        logInUiState = LoginUIState.Progress(false)
    }

    fun onSigninResult(result: SignInResult) {
        logInUiState = if (result.data != null){
            LoginUIState.LoginSuccess(true)
        }
        else if (result.errorMessage is CancellationException){
            LoginUIState.Progress(
                false
            )
        }
        else {
            LoginUIState.Error(
                BloomShowsErrorResponse(
                    message = result.errorMessage?.message,
                    errorCode = result.errorMessage?.cause?.message
                )
            )
        }
    }

    fun logIn(
        email: String,
        password: String,
    ) {
        viewModelScope.launch {
            logInUiState = LoginUIState.Progress(true)
            logInUiState = try {
                accountService.authenticate(email, password)
                LoginUIState.LoginSuccess(true) }
             catch (firebaseError: FirebaseException) {
                LoginUIState.Error(
                    BloomShowsErrorResponse(
                        message = firebaseError.localizedMessage,
                        errorCode = firebaseError.cause?.message.toString()
                    )
                )
            } catch (e: HttpException) {
                LoginUIState.Error(BloomShowsErrorResponse("UNKOWN", "Network Error"))
            } catch (e: ApiException) {
                LoginUIState.Error(
                    BloomShowsErrorResponse(
                        "404",
                        "Something went wrong. Try later!"
                    )
                )
            } catch (e: Exception) {
                LoginUIState.Error(
                    BloomShowsErrorResponse(
                        "System Error",
                        "Something went wrong. Try again!"
                    )
                )
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
            } catch (firebaseError: FirebaseException) {
                LoginUIState.Error(
                    BloomShowsErrorResponse(
                        message = firebaseError.localizedMessage,
                        errorCode = firebaseError.cause?.message + ":"
                    )
                )
            } catch (e: HttpException) {
                LoginUIState.Error(BloomShowsErrorResponse("UNKOWN", "Network Error"))
            } catch (e: ApiException) {
                Timber.tag("ui Exception").v(e.toString())

                LoginUIState.Error(
                    BloomShowsErrorResponse(
                        "404",
                        "Something went wrong. Try later!"
                    )
                )
            }
        }
    }

    fun signInAsAnonuymous(){
        viewModelScope.launch {
            logInUiState = LoginUIState.Progress(true)
            logInUiState = try {
                accountService.createAnonymousAccount()
                LoginUIState.LoginSuccess(true) }
            catch (firebaseError: FirebaseException) {
                LoginUIState.Error(
                    BloomShowsErrorResponse(
                        message = firebaseError.localizedMessage,
                        errorCode = firebaseError.cause?.message.toString()
                    )
                )
            } catch (e: HttpException) {
                LoginUIState.Error(BloomShowsErrorResponse("UNKOWN", "Network Error"))
            } catch (e: ApiException) {
                LoginUIState.Error(
                    BloomShowsErrorResponse(
                        "404",
                        "Something went wrong. Try later!"
                    )
                )
            } catch (e: Exception) {
                LoginUIState.Error(
                    BloomShowsErrorResponse(
                        "System Error",
                        "Something wentwrong. Try again!"
                    )
                )
            }
        }
    }
    fun logOut(navigate_to_login: () -> Unit) {
        viewModelScope.launch {
            accountService.signOut()
            googleAuthUiClient.signOut().also {
                navigate_to_login()
            }
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}


