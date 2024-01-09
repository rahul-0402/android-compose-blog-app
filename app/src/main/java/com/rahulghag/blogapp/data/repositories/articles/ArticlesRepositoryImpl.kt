package com.rahulghag.blogapp.data.repositories.articles

import com.rahulghag.blogapp.R
import com.rahulghag.blogapp.data.remote.ConduitApi
import com.rahulghag.blogapp.data.remote.mappers.ArticleDomainMapper
import com.rahulghag.blogapp.data.utils.parseErrorResponse
import com.rahulghag.blogapp.domain.models.Article
import com.rahulghag.blogapp.domain.repositories.ArticlesRepository
import com.rahulghag.blogapp.utils.Resource
import com.rahulghag.blogapp.utils.UiMessage
import retrofit2.HttpException
import java.io.IOException

class ArticlesRepositoryImpl(
    private val conduitApi: ConduitApi,
    private val articleDomainMapper: ArticleDomainMapper
) : ArticlesRepository {
    override suspend fun getArticles(offset: Int): Resource<List<Article>> {
        return try {
            val response =
                conduitApi.getArticles(offset)
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody?.articles != null) {
                    Resource.Success(data = articleDomainMapper.mapToDomainModel(responseBody.articles))
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
}