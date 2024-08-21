package com.example.githubusersearch.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class DetailUserResponse(
    val login: String,
    val id: Int,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    val name: String?,
    val followers: Int,
    val following: Int
)
