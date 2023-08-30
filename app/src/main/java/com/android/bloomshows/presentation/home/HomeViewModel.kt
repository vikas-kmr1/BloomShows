package com.android.bloomshows.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.bloomshows.data.local.preferences.UserPreferencesRepository
import com.android.bloomshows.presentation.splash.SplashViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val userPreferencesRepository: UserPreferencesRepository):ViewModel() {
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