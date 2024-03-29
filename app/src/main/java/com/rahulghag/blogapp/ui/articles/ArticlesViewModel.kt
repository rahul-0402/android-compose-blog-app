package com.rahulghag.blogapp.ui.articles

import androidx.lifecycle.viewModelScope
import com.rahulghag.blogapp.data.repositories.articles.ArticlesPaginator
import com.rahulghag.blogapp.domain.usecases.AddArticleToFavoritesCase
import com.rahulghag.blogapp.domain.usecases.AddCommentUseCase
import com.rahulghag.blogapp.domain.usecases.DeleteCommentUseCase
import com.rahulghag.blogapp.domain.usecases.FollowUseCase
import com.rahulghag.blogapp.domain.usecases.GetArticlesUseCase
import com.rahulghag.blogapp.domain.usecases.GetCommentsUseCase
import com.rahulghag.blogapp.domain.usecases.RemoveArticleFromFavoritesUseCase
import com.rahulghag.blogapp.domain.usecases.UnfollowUseCase
import com.rahulghag.blogapp.ui.base.BaseViewModel
import com.rahulghag.blogapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val getArticlesUseCase: GetArticlesUseCase,
    private val getCommentsUseCase: GetCommentsUseCase,
    private val deleteCommentUseCase: DeleteCommentUseCase,
    private val addCommentUseCase: AddCommentUseCase,
    private val followUseCase: FollowUseCase,
    private val unfollowUseCase: UnfollowUseCase,
    private val addArticleToFavoritesCase: AddArticleToFavoritesCase,
    private val removeArticleFromFavoritesUseCase: RemoveArticleFromFavoritesUseCase
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
                    articles = currentState.articles + items,
                    offset = newKey,
                    lastPageReached = items.isEmpty()
                )
            }
        }
    )

    override fun createInitialState() = ArticlesContract.State()

    override fun handleEvent(event: ArticlesContract.Event) {
        when (event) {
            is ArticlesContract.Event.GetArticles -> {
                getArticles()
            }

            is ArticlesContract.Event.RefreshArticles -> {
                refreshArticles()
            }

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

            is ArticlesContract.Event.CommentInputChange -> {
                setState { copy(comment = event.comment) }
            }

            ArticlesContract.Event.AddComment -> {
                addComment()
            }

            is ArticlesContract.Event.FollowUser -> {
                followUser(username = event.username)
            }

            is ArticlesContract.Event.UnfollowUser -> {
                unfollowUser(username = event.username)
            }

            ArticlesContract.Event.AddArticleToFavorites -> {
                addArticleToFavorites()
            }

            ArticlesContract.Event.RemoveArticleFromFavorites -> {
                removeArticleFromFavorites()
            }
        }
    }

    init {
        setEvent(ArticlesContract.Event.GetArticles)
    }

    private fun getArticles() = viewModelScope.launch {
        articlesPaginator.loadItems()
    }

    private fun refreshArticles() {
        articlesPaginator.reset()
        setState { copy(articles = emptyList()) }
        setEvent(ArticlesContract.Event.GetArticles)
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


    private fun addComment() = viewModelScope.launch {
        currentState.selectedArticle?.slug?.let { slug ->
            setState { copy(isLoading = true) }
            val result = addCommentUseCase.invoke(
                slug = slug,
                comment = currentState.comment
            )
            when (result) {
                is Resource.Success -> {
                    setState { copy(comment = "", isLoading = true) }
                    setEvent(ArticlesContract.Event.GetComments)
                    setEffect { ArticlesContract.Effect.NavigateToArticleDetails }
                    setEffect { ArticlesContract.Effect.ShowMessage(result.message) }
                }

                is Resource.Error -> {
                    setState { copy(comment = "", isLoading = true) }
                    setEffect { ArticlesContract.Effect.ShowMessage(result.message) }
                }
            }
        }
    }

    private fun followUser(
        username: String
    ) = viewModelScope.launch {
        when (val result = followUseCase.invoke(username = username)) {
            is Resource.Success -> {
                setState {
                    copy(
                        selectedArticle = selectedArticle?.copy(
                            author = selectedArticle.author?.copy(
                                isFollowing = true
                            )
                        )
                    )
                }
                setEvent(ArticlesContract.Event.RefreshArticles)
            }

            is Resource.Error -> {
                setEffect { ArticlesContract.Effect.ShowMessage(result.message) }
            }
        }
    }

    private fun unfollowUser(
        username: String
    ) = viewModelScope.launch {
        when (val result = unfollowUseCase.invoke(username = username)) {
            is Resource.Success -> {
                setState {
                    copy(
                        selectedArticle = selectedArticle?.copy(
                            author = selectedArticle.author?.copy(
                                isFollowing = false
                            )
                        )
                    )
                }
                setEvent(ArticlesContract.Event.RefreshArticles)
            }

            is Resource.Error -> {
                setEffect { ArticlesContract.Effect.ShowMessage(result.message) }
            }
        }
    }

    private fun addArticleToFavorites() = viewModelScope.launch {
        currentState.selectedArticle?.slug?.let { slug ->
            val result = addArticleToFavoritesCase.invoke(
                slug = slug
            )
            when (result) {
                is Resource.Success -> {
                    setState {
                        copy(selectedArticle = selectedArticle?.copy(isFavorite = true))
                    }
                    setEvent(ArticlesContract.Event.RefreshArticles)
                }

                is Resource.Error -> {
                    setEffect { ArticlesContract.Effect.ShowMessage(result.message) }
                }
            }
        }
    }

    private fun removeArticleFromFavorites() = viewModelScope.launch {
        currentState.selectedArticle?.slug?.let { slug ->
            val result = removeArticleFromFavoritesUseCase.invoke(
                slug = slug
            )
            when (result) {
                is Resource.Success -> {
                    setState {
                        copy(selectedArticle = selectedArticle?.copy(isFavorite = false))
                    }
                    setEvent(ArticlesContract.Event.RefreshArticles)
                }

                is Resource.Error -> {
                    setEffect { ArticlesContract.Effect.ShowMessage(result.message) }
                }
            }
        }
    }
}