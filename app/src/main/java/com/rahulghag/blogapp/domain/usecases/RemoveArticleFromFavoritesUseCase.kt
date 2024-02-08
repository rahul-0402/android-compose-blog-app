package com.rahulghag.blogapp.domain.usecases

import com.rahulghag.blogapp.domain.repositories.ArticlesRepository
import com.rahulghag.blogapp.utils.Resource

class RemoveArticleFromFavoritesUseCase(
    private val articlesRepository: ArticlesRepository,
) {
    suspend operator fun invoke(slug: String): Resource<Any?> {
        return articlesRepository.removeArticleFromFavorites(slug = slug)
    }
}