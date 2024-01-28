package com.rahulghag.blogapp.ui.articles

import androidx.lifecycle.viewModelScope
import com.rahulghag.blogapp.data.repositories.articles.ArticlesPaginator
import com.rahulghag.blogapp.domain.usecases.DeleteCommentUseCase
import com.rahulghag.blogapp.domain.usecases.GetArticlesUseCase
import com.rahulghag.blogapp.domain.usecases.GetCommentsUseCase
import com.rahulghag.blogapp.ui.base.BaseViewModel
import com.rahulghag.blogapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val getArticlesUseCase: GetArticlesUseCase,
    private val getCommentsUseCase: GetCommentsUseCase,
    private val deleteCommentUseCase: DeleteCommentUseCase
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
                setState { copy(selectedArticle = event.article, comments = emptyList()) }
                setEvent(ArticlesContract.Event.GetComments)
                setEffect { ArticlesContract.Effect.NavigateToArticleDetails }
            }

            is ArticlesContract.Event.GetComments -> {
                getComments()
            }

            is ArticlesContract.Event.DeleteComment -> {
                deleteComment(id = event.id)
            }
        }
    }

    init {
        getArticles()
    }

    fun getArticles() = viewModelScope.launch {
        articlesPaginator.loadItems()
    }

    private fun getComments() = viewModelScope.launch {
        currentState.selectedArticle?.slug?.let { slug ->
            setState { copy(isLoading = true) }
            val result = getCommentsUseCase.invoke(
                slug = slug
            )
            when (result) {
                is Resource.Success -> {
                    setState {
                        copy(
                            comments = result.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                }

                is Resource.Error -> {
                    setState { copy(isLoading = false) }
                    setEffect { ArticlesContract.Effect.ShowMessage(result.message) }
                }
            }
        }
    }

    private fun deleteComment(id: Int) = viewModelScope.launch {
        currentState.selectedArticle?.slug?.let { slug ->
            val result = deleteCommentUseCase.invoke(
                slug = slug,
                id = id
            )
            when (result) {
                is Resource.Success -> {
                    setState { copy(comments = currentState.comments.filterNot { it.id == id }) }
                }

                is Resource.Error -> {
                    setEffect { ArticlesContract.Effect.ShowMessage(result.message) }
                }
            }
        }
    }
}