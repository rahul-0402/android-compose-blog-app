package com.rahulghag.blogapp.data.repositories.articles

import com.rahulghag.blogapp.data.utils.Paginator
import com.rahulghag.blogapp.domain.models.Article
import com.rahulghag.blogapp.utils.Resource
import com.rahulghag.blogapp.utils.UiMessage

class ArticlesPaginator<Key>(
    private val initialKey: Key,
    private inline val onLoadComplete: (Boolean) -> Unit,
    private inline val onRequest: suspend (nextKey: Key) -> Resource<List<Article>>,
    private inline val getNextKey: suspend (List<Article>) -> Key,
    private inline val onError: suspend (UiMessage?) -> Unit,
    private inline val onSuccess: suspend (items: List<Article>, newKey: Key) -> Unit
) : Paginator<Key> {

    private var currentKey = initialKey
    private var requestInProgress = false

    override suspend fun loadItems() {
        if (requestInProgress) {
            return
        }
        requestInProgress = true
        onLoadComplete(true)
        val result = onRequest(currentKey)
        requestInProgress = false
        when (result) {
            is Resource.Success -> {
                currentKey = getNextKey(result.data ?: emptyList())
                onSuccess(result.data ?: emptyList(), currentKey)
                onLoadComplete(false)
            }

            is Resource.Error -> {
                onError(result.message)
                onLoadComplete(false)
            }
        }
    }

    override fun reset() {
        currentKey = initialKey
    }
}