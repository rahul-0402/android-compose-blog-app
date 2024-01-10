package com.rahulghag.blogapp.domain.usecases

import com.rahulghag.blogapp.domain.models.Article
import com.rahulghag.blogapp.domain.repositories.ArticlesRepository
import com.rahulghag.blogapp.utils.Resource

class GetArticlesUseCase(
    private val articlesRepository: ArticlesRepository,
) {
    suspend operator fun invoke(offset: Int): Resource<List<Article>> {
        return articlesRepository.getArticles(offset = offset)
    }
}