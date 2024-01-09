package com.rahulghag.blogapp.data.remote.dtos.response


import com.google.gson.annotations.SerializedName

data class ArticlesResponse(
    @SerializedName("articles")
    val articles: List<ArticleDto>?,
    @SerializedName("articlesCount")
    val articlesCount: Int?
)

data class ArticleDto(
    @SerializedName("author")
    val authorDto: AuthorDto?,
    @SerializedName("body")
    val body: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("favorited")
    val isFavorite: Boolean?,
    @SerializedName("favoritesCount")
    val favoritesCount: Int?,
    @SerializedName("slug")
    val slug: String?,
    @SerializedName("tagList")
    val tagList: List<String?>?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?
)