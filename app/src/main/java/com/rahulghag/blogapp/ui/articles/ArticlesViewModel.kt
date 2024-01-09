package com.rahulghag.blogapp.ui.articles

import androidx.lifecycle.viewModelScope
import com.rahulghag.blogapp.data.repositories.articles.ArticlesPaginator
import com.rahulghag.blogapp.domain.repositories.ArticlesRepository
import com.rahulghag.blogapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val articlesRepository: ArticlesRepository
) : BaseViewModel<ArticlesContract.State, ArticlesContract.Event, ArticlesContract.Effect>() {

    private val articlesPaginator = ArticlesPaginator(
        initialKey = currentState.page,
        onLoadComplete = {
            setState { copy(isLoading = it) }
        },
        onRequest = { nextPage ->
            articlesRepository.getArticles(nextPage)
        },
        getNextKey = {
            currentState.page + it.size
        },
        onError = {
            setEffect { ArticlesContract.Effect.ShowMessage(uiMessage = it) }
        },
        onSuccess = { items, newKey ->
            setState {
                copy(
                    items = currentState.items + items,
                    page = newKey,
                    lastPageReached = items.isEmpty()
                )
            }
        }
    )

    override fun createInitialState() = ArticlesContract.State()

    override fun handleEvent(event: ArticlesContract.Event) {
        TODO("Not yet implemented")
    }

    init {
        getArticles()
    }

    fun getArticles() = viewModelScope.launch {
        articlesPaginator.loadItems()
    }
}