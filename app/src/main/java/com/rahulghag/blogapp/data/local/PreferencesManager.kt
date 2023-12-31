package com.rahulghag.blogapp.data.local

import kotlinx.coroutines.flow.Flow

interface PreferencesManager {
    suspend fun saveToken(token: String)

    fun getToken(): Flow<String>
}