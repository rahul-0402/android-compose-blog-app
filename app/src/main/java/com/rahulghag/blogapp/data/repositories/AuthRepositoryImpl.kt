package com.rahulghag.blogapp.data.repositories

import com.rahulghag.blogapp.R
import com.rahulghag.blogapp.data.remote.ConduitApi
import com.rahulghag.blogapp.data.remote.dtos.request.UserRequest
import com.rahulghag.blogapp.data.remote.dtos.request.UserRequestDto
import com.rahulghag.blogapp.data.remote.mappers.UserDomainMapper
import com.rahulghag.blogapp.data.utils.parseErrorResponse
import com.rahulghag.blogapp.domain.models.User
import com.rahulghag.blogapp.domain.repositories.AuthRepository
import com.rahulghag.blogapp.utils.Resource
import com.rahulghag.blogapp.utils.UiMessage
import retrofit2.HttpException
import java.io.IOException

class AuthRepositoryImpl(
    private val conduitApi: ConduitApi,
    private val userDomainMapper: UserDomainMapper
) : AuthRepository {
    override suspend fun login(email: String, password: String): Resource<User> {
        val userRequest = UserRequest(UserRequestDto(email = email, password = password))
        return try {
            val response =
                conduitApi.login(
                    userRequest = userRequest
                )
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody?.userDto != null) {
                    val user = userDomainMapper.mapToDomainModel(responseBody.userDto)
                    Resource.Success(data = user)
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

    override suspend fun register(
        username: String,
        email: String,
        password: String
    ): Resource<User> {
        val userRequest =
            UserRequest(UserRequestDto(username = username, email = email, password = password))
        return try {
            val response =
                conduitApi.register(
                    userRequest = userRequest
                )
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody?.userDto != null) {
                    val user = userDomainMapper.mapToDomainModel(responseBody.userDto)
                    Resource.Success(data = user)
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

//    override suspend fun login(email: String, password: String): Resource<User> {
//        val userRequest = UserRequest(UserRequestDto(email = email, password = password))
//        return baseApiCall(
//            apiCall = { conduitApi.login(userRequest = userRequest) },
//            domainMapper = { responseBody ->
//                responseBody.userDto.let { userDomainMapper.mapToDomainModel(it) }
//            },
//            onSuccess = { data->
//
//            }
//        )
//    }
}