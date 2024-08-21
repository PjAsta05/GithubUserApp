package com.example.githubusersearch.core.data.source.remote.network

import com.example.githubusersearch.core.BuildConfig
import com.example.githubusersearch.core.data.source.remote.response.DetailUserResponse
import com.example.githubusersearch.core.data.source.remote.response.UserData
import com.example.githubusersearch.core.data.source.remote.response.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token ${BuildConfig.API_Key}")
    suspend fun getSearchUsers(
        @Query("q") query: String
    ): Response<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token ${BuildConfig.API_Key}")
    suspend fun getUserDetail(
        @Path("username") username: String
    ): Response<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token ${BuildConfig.API_Key}")
    suspend fun getFollowers(
        @Path("username") username: String
    ): Response<List<UserData>>

    @GET("users/{username}/following")
    @Headers("Authorization: token ${BuildConfig.API_Key}")
    suspend fun getFollowing(
        @Path("username") username: String
    ): Response<List<UserData>>
}
