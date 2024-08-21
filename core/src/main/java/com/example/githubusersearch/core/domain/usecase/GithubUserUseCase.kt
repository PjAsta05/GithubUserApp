package com.example.githubusersearch.core.domain.usecase

import com.example.githubusersearch.core.domain.model.DetailUser
import com.example.githubusersearch.core.domain.model.Favorite
import com.example.githubusersearch.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface GithubUserUseCase {
    suspend fun getSearchUsers(query: String): Flow<List<User>?>
    suspend fun getUserDetail(username: String): Flow<DetailUser?>
    suspend fun getFollowers(username: String): Flow<List<User>?>
    suspend fun getFollowing(username: String): Flow<List<User>?>
    fun getFavoriteUsers(): Flow<List<Favorite>>
    fun checkUser(id: Int): Flow<Favorite?>
    suspend fun addFavoriteUser(favoriteUser: Favorite)
    suspend fun removeFavoriteUser(favoriteUser: Favorite)
}