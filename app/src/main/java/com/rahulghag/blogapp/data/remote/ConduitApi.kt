package com.rahulghag.blogapp.data.remote

import com.rahulghag.blogapp.data.remote.dtos.request.UserRequest
import com.rahulghag.blogapp.data.remote.dtos.response.ArticlesResponse
import com.rahulghag.blogapp.data.remote.dtos.response.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ConduitApi {
    @POST("users/login")
    suspend fun login(
        @Body userRequest: UserRequest
    ): Response<UserResponse>

    @POST("users")
    suspend fun createAccount(
        @Body userRequest: UserRequest
    ): Response<UserResponse>

    @GET("articles")
    suspend fun getArticles(
        @Query("offset") offset: Int
    ): Response<ArticlesResponse>
}