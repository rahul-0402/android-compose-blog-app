package com.rahulghag.blogapp.data.remote.mappers

import com.rahulghag.blogapp.data.remote.dtos.response.ArticleDto
import com.rahulghag.blogapp.data.utils.DomainMapper
import com.rahulghag.blogapp.domain.models.Article

class ArticleDomainMapper(private val authorDomainMapper: AuthorDomainMapper) :
    DomainMapper<List<ArticleDto>, List<Article>> {
    override fun mapToDomainModel(data: List<ArticleDto>): List<Article> {
        return data.map { articleDto ->
            Article(
                author = authorDomainMapper.mapToDomainModel(articleDto.authorDto),
                body = articleDto.body,
                createdAt = articleDto.createdAt,
                description = articleDto.description,
                isFavorite = articleDto.isFavorite,
                favoritesCount = articleDto.favoritesCount,
                slug = articleDto.slug,
                tagList = articleDto.tagList,
                title = articleDto.title,
                updatedAt = articleDto.updatedAt
            )
        }
    }
}