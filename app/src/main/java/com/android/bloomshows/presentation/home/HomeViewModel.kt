package com.android.bloomshows.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.android.bloomshows.data.repository.movie.MovieRepository
import com.android.bloomshows.data.repository.preferences.UserPreferencesRepository
import com.android.bloomshows.network.services.auth.AccountService
import com.android.bloomshows.utils.MediaCategories
import com.android.bloomshows.utils.Time_Window
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
    accountService: AccountService,
    private val movieRepository: MovieRepository
) : ViewModel() {

    //userdata
    val currentUser = accountService.currentUser.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = null
    )

    val hasuser: Boolean = accountService.hasUser
    val userId = accountService.currentUserId

    val trendingPagingFlow = movieRepository.fetchMovieList(
        language = "en-US",
        timeWindow = Time_Window.DAY,
        category = MediaCategories.TRENDING
    ).cachedIn(viewModelScope)

    val popularPagingFlow = movieRepository.fetchMovieList(
        language = "en-US",
        timeWindow = Time_Window.DAY,
        category = MediaCategories.POPULAR
    ).cachedIn(viewModelScope)

    val nowPlayingPagingFlow = movieRepository.fetchMovieList(
        language = "en-US",
        timeWindow = Time_Window.DAY,
        category = MediaCategories.NOW_PLAYING
    ).cachedIn(viewModelScope)

    val isFirstTime: StateFlow<Boolean> = userPreferencesRepository.isUserFirstTime.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = true
    )

    fun saveUserFirstTime(value: Boolean = false) {
        viewModelScope.launch(Dispatchers.IO) {
            userPreferencesRepository.saveFirstTimePreference(value)

        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

}