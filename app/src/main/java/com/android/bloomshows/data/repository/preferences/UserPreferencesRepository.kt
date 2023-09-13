package com.android.bloomshows.data.repository.preferences

import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository{
    suspend fun saveFirstTimePreference(isFirstTime: Boolean)
    val isUserFirstTime:Flow<Boolean>

}