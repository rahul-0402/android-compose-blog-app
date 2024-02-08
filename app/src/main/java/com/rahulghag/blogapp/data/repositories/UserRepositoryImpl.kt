package com.rahulghag.blogapp.data.repositories

import com.rahulghag.blogapp.R
import com.rahulghag.blogapp.data.remote.ConduitApi
import com.rahulghag.blogapp.data.utils.parseErrorResponse
import com.rahulghag.blogapp.domain.repositories.UserRepository
import com.rahulghag.blogapp.utils.Resource
import com.rahulghag.blogapp.utils.UiMessage
import retrofit2.HttpException
import java.io.IOException

class UserRepositoryImpl(
    private val conduitApi: ConduitApi,
) : UserRepository {
    override suspend fun followUser(username: String): Resource<Any?> {
        return try {
            val response =
                conduitApi.followUser(username)
            if (response.isSuccessful) {
                Resource.Success(data = null)
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

    override suspend fun unfollowUser(username: String): Resource<Any?> {
        return try {
            val response =
                conduitApi.unFollowUser(username)
            if (response.isSuccessful) {
                Resource.Success(data = null)
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