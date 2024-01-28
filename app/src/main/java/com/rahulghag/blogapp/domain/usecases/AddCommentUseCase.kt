package com.rahulghag.blogapp.domain.usecases

import com.rahulghag.blogapp.R
import com.rahulghag.blogapp.domain.repositories.ArticlesRepository
import com.rahulghag.blogapp.utils.Resource
import com.rahulghag.blogapp.utils.UiMessage

class AddCommentUseCase(
    private val articlesRepository: ArticlesRepository,
) {
    suspend operator fun invoke(slug: String, comment: String): Resource<Any?> {
        if (comment.isEmpty()) {
            return Resource.Error(message = UiMessage.StringResource(R.string.comment_cannot_be_blank))
        }

        return articlesRepository.addComment(slug = slug, comment = comment)
    }
}