package com.rahulghag.blogapp.data.repositories.articles

import com.rahulghag.blogapp.R
import com.rahulghag.blogapp.data.remote.ConduitApi
import com.rahulghag.blogapp.data.remote.mappers.ArticlesMapper
import com.rahulghag.blogapp.data.remote.mappers.CommentsMapper
import com.rahulghag.blogapp.data.utils.parseErrorResponse
import com.rahulghag.blogapp.domain.models.Article
import com.rahulghag.blogapp.domain.models.Comment
import com.rahulghag.blogapp.domain.repositories.ArticlesRepository
import com.rahulghag.blogapp.utils.Resource
import com.rahulghag.blogapp.utils.UiMessage
import retrofit2.HttpException
import java.io.IOException

class ArticlesRepositoryImpl(
    private val conduitApi: ConduitApi,
    private val articlesMapper: ArticlesMapper,
    private val commentsMapper: CommentsMapper
) : ArticlesRepository {
    override suspend fun getArticles(offset: Int): Resource<List<Article>> {
        return try {
            val response =
                conduitApi.getArticles(offset)
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody?.articles != null) {
                    Resource.Success(data = articlesMapper.mapToDomainModel(responseBody.articles))
                } else {
                    Resource.Error(message = UiMessage.StringResource(R.string.error_something_went_wrong))
                }
            } else {
                Resource.Error(message = parseErrorResponse(errorBody = response.errorBody()))
            }
        } catch (exception: Exception) {
            return when (exception) {
                is IOException -> Resource.Error(message = UiMessage.StringResource(R.string.error_no_internet_connection))
                is HttpException -> Resource.Error(message = UiMessage.StringResource(R.string.error_something_went_wrong))
                else -> Resource.Error(message = UiMessage.StringResource(R.string.error_something_went_wrong))
            }
        }
    }

    override suspend fun getComments(slug: String): Resource<List<Comment>> {
        return try {
            val response =
                conduitApi.getComments(slug)
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody?.comments != null) {
                    Resource.Success(data = commentsMapper.mapToDomainModel(responseBody.comments))
                } else {
                    Resource.Error(message = UiMessage.StringResource(R.string.error_something_went_wrong))
                }
            } else {
                Resource.Error(message = parseErrorResponse(errorBody = response.errorBody()))
            }
        } catch (exception: Exception) {
            return when (exception) {
                is IOException -> Resource.Error(message = UiMessage.StringResource(R.string.error_no_internet_connection))
                is HttpException -> Resource.Error(message = UiMessage.StringResource(R.string.error_something_went_wrong))
                else -> Resource.Error(message = UiMessage.StringResource(R.string.error_something_went_wrong))
            }
        }
    }

    override suspend fun deleteComment(slug: String, id: Int): Resource<Any?> {
        return try {
            val response =
                conduitApi.deleteComment(slug = slug, id = id)
            if (response.isSuccessful) {
                Resource.Success(data = null, message = null)
            } else {
                Resource.Error(message = parseErrorResponse(errorBody = response.errorBody()))
            }
        } catch (exception: Exception) {
            return when (exception) {
                is IOException -> Resource.Error(message = UiMessage.StringResource(R.string.error_no_internet_connection))
                is HttpException -> Resource.Error(message = UiMessage.StringResource(R.string.error_something_went_wrong))
                else -> Resource.Error(message = UiMessage.StringResource(R.string.error_something_went_wrong))
            }
        }
    }
}