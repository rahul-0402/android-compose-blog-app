package com.rahulghag.blogapp.data.remote

import com.rahulghag.blogapp.data.remote.dtos.request.UserRequest
import com.rahulghag.blogapp.data.remote.dtos.response.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ConduitApi {
    @POST("users/login")
    suspend fun login(
        @Body userRequest: UserRequest
    ): Response<UserResponse>

    @POST("users")
    suspend fun register(
        @Body userRequest: UserRequest
    ): Response<UserResponse>
}