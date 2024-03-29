package com.rahulghag.blogapp.domain.repositories

import com.rahulghag.blogapp.domain.models.Article
import com.rahulghag.blogapp.domain.models.Comment
import com.rahulghag.blogapp.utils.Resource

interface ArticlesRepository {
    suspend fun getArticles(offset: Int): Resource<List<Article>>
    suspend fun getComments(slug: String): Resource<List<Comment>>
    suspend fun deleteComment(slug: String, id: Int): Resource<Any?>
    suspend fun addComment(slug: String, comment: String): Resource<Any?>
    suspend fun addArticleToFavorites(slug: String): Resource<Any?>
    suspend fun removeArticleFromFavorites(slug: String): Resource<Any?>
}