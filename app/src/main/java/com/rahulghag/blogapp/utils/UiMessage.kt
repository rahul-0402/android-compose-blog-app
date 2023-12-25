package com.rahulghag.blogapp.utils

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class UiMessage {
    data class DynamicMessage(val value: String) : UiMessage()
    class StringResource(
        @StringRes val resourceId: Int,
        vararg val formatArgs: Any
    ) : UiMessage()

    fun asString(context: Context): String {
        return when (this) {
            is DynamicMessage -> value
            is StringResource -> context.getString(resourceId, *formatArgs)
        }
    }

    @Composable
    fun asString(): String {
        return when (this) {
            is DynamicMessage -> value
            is StringResource -> stringResource(id = resourceId, *formatArgs)
        }
    }
}