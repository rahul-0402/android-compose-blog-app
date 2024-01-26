package com.rahulghag.blogapp.domain.usecases

import com.rahulghag.blogapp.domain.models.Comment
import com.rahulghag.blogapp.domain.repositories.ArticlesRepository
import com.rahulghag.blogapp.utils.Resource

class GetCommentsUseCase(
    private val articlesRepository: ArticlesRepository,
) {
    suspend operator fun invoke(slug: String): Resource<List<Comment>> {
        return articlesRepository.getComments(slug = slug)
    }
}