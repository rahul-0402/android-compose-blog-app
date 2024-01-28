package com.rahulghag.blogapp.domain.usecases

import com.rahulghag.blogapp.domain.repositories.ArticlesRepository
import com.rahulghag.blogapp.utils.Resource

class DeleteCommentUseCase(
    private val articlesRepository: ArticlesRepository,
) {
    suspend operator fun invoke(slug: String, id: Int): Resource<Any?> {
        return articlesRepository.deleteComment(slug = slug, id = id)
    }
}