package com.example.githubusersearch.core.data.source.remote.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class UserResponse(
    val items: ArrayList<UserData>
)

@Keep
data class UserData(
    val login: String,
    val id: Int,
    @SerializedName("avatar_url")
    val avatarUrl: String
)
