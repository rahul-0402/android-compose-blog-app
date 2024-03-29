package com.rahulghag.blogapp.data.remote

import com.rahulghag.blogapp.data.remote.dtos.request.AddCommentRequest
import com.rahulghag.blogapp.data.remote.dtos.request.UserRequest
import com.rahulghag.blogapp.data.remote.dtos.response.ArticlesResponse
import com.rahulghag.blogapp.data.remote.dtos.response.CommentsResponse
import com.rahulghag.blogapp.data.remote.dtos.response.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
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

    @GET("/api/articles/{slug}/comments")
    suspend fun getComments(
        @Path("slug") slug: String
    ): Response<CommentsResponse>

    @DELETE("/api/articles/{slug}/comments/{id}")
    suspend fun deleteComment(
        @Path("slug") slug: String,
        @Path("id") id: Int
    ): Response<Any>

    @POST("/api/articles/{slug}/comments")
    suspend fun addComment(
        @Path("slug") slug: String,
        @Body addCommentRequest: AddCommentRequest
    ): Response<Any>

    @POST("/api/profiles/{username}/follow")
    suspend fun followUser(
        @Path("username") username: String
    ): Response<Any>

    @DELETE("/api/profiles/{username}/follow")
    suspend fun unFollowUser(
        @Path("username") username: String
    ): Response<Any>

    @POST("/api/articles/{slug}/favorite")
    suspend fun addArticleToFavorites(
        @Path("slug") slug: String
    ): Response<Any>

    @DELETE("/api/articles/{slug}/favorite")
    suspend fun removeArticleFromFavorites(
        @Path("slug") slug: String
    ): Response<Any>
}