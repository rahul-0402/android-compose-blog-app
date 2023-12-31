package com.rahulghag.blogapp.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class PreferencesManagerImpl(private val dataStore: DataStore<Preferences>) : PreferencesManager {
    private object PreferenceKeys {
        val KEY_TOKEN = stringPreferencesKey("token")
    }

    override suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.KEY_TOKEN] = token
        }
    }

    override fun getToken(): Flow<String> {
        return dataStore.data.catch { emit(emptyPreferences()) }.map { preferences ->
            preferences[PreferenceKeys.KEY_TOKEN] ?: ""
        }
    }
}