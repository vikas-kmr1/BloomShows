package com.android.bloomshows.data.local.preferences

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject


class UserPreferencesRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : UserPreferencesRepository {
    override suspend fun saveFirstTimePreference(isFirstTime: Boolean) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[IS_FIRST_TIME] = isFirstTime
        }
    }

    override val isUserFirstTime: Flow<Boolean> = dataStore.data
        .catch { it ->
            if (it is IOException) {
                Timber.tag(TAG).e(it, "Error reading preferences.")
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preference ->

            preference[IS_FIRST_TIME] ?: true
        }

    private companion object {
        val IS_FIRST_TIME = booleanPreferencesKey("is_first_time")
        const val TAG = "UserPreferencesRepo"
    }

}