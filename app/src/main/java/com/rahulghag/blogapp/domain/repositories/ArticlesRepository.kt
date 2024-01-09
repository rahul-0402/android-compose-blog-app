package com.rahulghag.blogapp.domain.repositories

import com.rahulghag.blogapp.domain.models.Article
import com.rahulghag.blogapp.utils.Resource

interface ArticlesRepository {
    suspend fun getArticles(offset: Int): Resource<List<Article>>
}