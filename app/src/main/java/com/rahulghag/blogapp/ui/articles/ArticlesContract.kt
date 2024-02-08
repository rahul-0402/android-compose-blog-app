package com.rahulghag.blogapp.ui.articles

import com.rahulghag.blogapp.domain.models.Article
import com.rahulghag.blogapp.domain.models.Comment
import com.rahulghag.blogapp.ui.base.UiEffect
import com.rahulghag.blogapp.ui.base.UiEvent
import com.rahulghag.blogapp.ui.base.UiState
import com.rahulghag.blogapp.utils.UiMessage

class ArticlesContract {
    data class State(
        val articles: List<Article> = emptyList(),
        val offset: Int = 0,
        val lastPageReached: Boolean = false,
        val isLoading: Boolean = false,
        val selectedArticle: Article? = null,
        val comments: List<Comment> = emptyList(),
        val comment: String = ""
    ) : UiState

    sealed class Event : UiEvent {

        data object GetArticles : Event()
        data object RefreshArticles : Event()
        data class SelectArticle(val article: Article) : Event()
        data object GetComments : Event()
        data class DeleteComment(val id: Int) : Event()
        data class CommentInputChange(val comment: String) : Event()
        data object AddComment : Event()
        data class FollowUser(val username: String) : Event()
        data class UnfollowUser(val username: String) : Event()
    }

    sealed class Effect : UiEffect {
        data object NavigateToArticleDetails : Effect()
        data class ShowMessage(val uiMessage: UiMessage?) : Effect()
    }
}