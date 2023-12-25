package com.rahulghag.blogapp.utils

sealed class Resource<T>(
    val data: T? = null,
    val message: UiMessage? = null,
) {
    class Success<T>(data: T, message: UiMessage? = null) : Resource<T>(data, message)
    class Error<T>(data: T? = null, message: UiMessage) : Resource<T>(data, message)
}