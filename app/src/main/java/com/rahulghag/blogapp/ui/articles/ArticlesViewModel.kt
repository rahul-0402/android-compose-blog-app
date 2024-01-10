package com.rahulghag.blogapp.ui.articles

import androidx.lifecycle.viewModelScope
import com.rahulghag.blogapp.data.repositories.articles.ArticlesPaginator
import com.rahulghag.blogapp.domain.usecases.GetArticlesUseCase
import com.rahulghag.blogapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val getArticlesUseCase: GetArticlesUseCase
) : BaseViewModel<ArticlesContract.State, ArticlesContract.Event, ArticlesContract.Effect>() {

    private val articlesPaginator = ArticlesPaginator(
        initialKey = currentState.offset,
        onLoadComplete = {
            setState { copy(isLoading = it) }
        },
        onRequest = { nextKey ->
            getArticlesUseCase.invoke(offset = nextKey)
        },
        getNextKey = {
            currentState.offset + it.size
        },
        onError = {
            setEffect { ArticlesContract.Effect.ShowMessage(uiMessage = it) }
        },
        onSuccess = { items, newKey ->
            setState {
                copy(
                    items = currentState.items + items,
                    offset = newKey,
                    lastPageReached = items.isEmpty()
                )
            }
        }
    )
    override fun createInitialState() = ArticlesContract.State()
    override fun handleEvent(event: ArticlesContract.Event) {
        when (event) {
            is ArticlesContract.Event.SelectArticle -> {
                setState { copy(selectedArticle = event.article) }
                setEffect { ArticlesContract.Effect.NavigateToArticleDetails }
            }
        }
    }

    init {
        getArticles()
    }

    fun getArticles() = viewModelScope.launch {
        articlesPaginator.loadItems()
    }
}