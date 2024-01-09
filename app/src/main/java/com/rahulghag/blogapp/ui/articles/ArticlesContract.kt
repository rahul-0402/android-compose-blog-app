package com.rahulghag.blogapp.ui.articles

import com.rahulghag.blogapp.domain.models.Article
import com.rahulghag.blogapp.ui.base.UiEffect
import com.rahulghag.blogapp.ui.base.UiEvent
import com.rahulghag.blogapp.ui.base.UiState
import com.rahulghag.blogapp.utils.UiMessage

class ArticlesContract {
    data class State(
        val items: List<Article> = emptyList(),
        val page: Int = 0,
        val lastPageReached: Boolean = false,
        val isLoading: Boolean = false,
    ) : UiState

    sealed class Event : UiEvent

    sealed class Effect : UiEffect {
        data class ShowMessage(val uiMessage: UiMessage?) : ArticlesContract.Effect()
    }
}