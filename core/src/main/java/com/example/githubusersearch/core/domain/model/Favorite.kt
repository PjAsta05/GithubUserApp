package com.example.githubusersearch.core.domain.model

data class Favorite(
    var id: Int = 0,
    var login: String = "",
    var avatarUrl: String? = "null"
)
