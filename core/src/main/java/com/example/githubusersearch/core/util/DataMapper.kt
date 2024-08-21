package com.example.githubusersearch.core.util

import com.example.githubusersearch.core.data.source.local.entity.FavoriteUser
import com.example.githubusersearch.core.data.source.remote.response.DetailUserResponse
import com.example.githubusersearch.core.data.source.remote.response.UserData
import com.example.githubusersearch.core.data.source.remote.response.UserResponse
import com.example.githubusersearch.core.domain.model.DetailUser
import com.example.githubusersearch.core.domain.model.Favorite
import com.example.githubusersearch.core.domain.model.User

object DataMapper {
    private fun mapUserDataToDomain(input: UserData): User {
        return User(
            login = input.login,
            id = input.id,
            avatarUrl = input.avatarUrl
        )
    }

    fun mapUserResponseToDomain(input: UserResponse): List<User> {
        return input.items.map {
            mapUserDataToDomain(it)
        }
    }

    fun mapDetailUserResponseToDomain(input: DetailUserResponse): DetailUser {
        return DetailUser(
            login = input.login,
            id = input.id,
            avatarUrl = input.avatarUrl,
            name = input.name,
            followers = input.followers,
            following = input.following
        )
    }

    fun mapFollowListResponseToDomain(input: List<UserData>): List<User> {
        return input.map { userData ->
            User(
                login = userData.login,
                id = userData.id,
                avatarUrl = userData.avatarUrl
            )
        }
    }

    fun mapFavoriteListToDomain(input: List<FavoriteUser>): List<Favorite> {
        return input.map {
            Favorite(
                login = it.login,
                id = it.id,
                avatarUrl = it.avatarUrl
            )
        }
    }

    fun mapFavoriteToDomain(input: FavoriteUser): Favorite {
        return Favorite(
            login = input.login,
            id = input.id,
            avatarUrl = input.avatarUrl
        )
    }

    fun mapFavoriteToData(input: Favorite): FavoriteUser {
        return FavoriteUser(
            login = input.login,
            id = input.id,
            avatarUrl = input.avatarUrl
        )
    }
}