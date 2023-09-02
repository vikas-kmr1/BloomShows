package com.android.bloomshows.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.bloomshows.data.local.preferences.UserPreferencesRepository
import com.android.bloomshows.network.services.auth.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val accountService: AccountService
) : ViewModel() {

    //userdata
    val currentUser = accountService.currentUser.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = null
    )

    val hasuser: Boolean = accountService.hasUser
    val userId = accountService.currentUserId


    val isFirstTime: StateFlow<Boolean> = userPreferencesRepository.isUserFirstTime.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = true
    )

    fun saveUserFirstTime() {
        viewModelScope.launch(Dispatchers.IO) {
            userPreferencesRepository.saveFirstTimePreference(false)

        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

}