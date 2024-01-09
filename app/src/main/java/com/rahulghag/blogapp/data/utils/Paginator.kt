package com.rahulghag.blogapp.data.utils

interface Paginator<Key> {
    suspend fun loadItems()
    fun reset()
}