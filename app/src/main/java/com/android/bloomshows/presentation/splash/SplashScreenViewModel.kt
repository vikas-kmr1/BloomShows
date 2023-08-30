package com.android.bloomshows.presentation.splash

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.android.bloomshows.data.local.preferences.UserPreferencesRepository
import com.android.bloomshows.network.services.auth.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Locale.filter
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val accountService: AccountService
) :
    ViewModel() {

     val isFirstTime: StateFlow<Boolean> = userPreferencesRepository.isUserFirstTime.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = true
            )

    fun onAppStart(openHome: () -> Unit, openLogIn: ()-> Unit) {
        if (accountService.hasUser) {
            openHome()
        }
        else {openLogIn()}
    }

    /**
     * save user name
     * */
    fun saveUserFirstTime() {
        viewModelScope.launch(Dispatchers.IO) {
            userPreferencesRepository.saveFirstTimePreference(false)

        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}