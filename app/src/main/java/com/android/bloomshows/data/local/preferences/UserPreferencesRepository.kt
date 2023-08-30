package com.android.bloomshows.data.local.preferences

import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository{
    suspend fun saveFirstTimePreference(isFirstTime: Boolean)
    val isUserFirstTime:Flow<Boolean>

}