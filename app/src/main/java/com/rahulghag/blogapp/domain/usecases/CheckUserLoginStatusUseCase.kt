package com.rahulghag.blogapp.domain.usecases

import com.rahulghag.blogapp.data.local.PreferencesManager
import kotlinx.coroutines.flow.firstOrNull

class CheckUserLoginStatusUseCase(
    private val preferencesManager: PreferencesManager
) {
    suspend operator fun invoke(): Boolean {
        return preferencesManager.getToken().firstOrNull().isNullOrEmpty().not()
    }
}