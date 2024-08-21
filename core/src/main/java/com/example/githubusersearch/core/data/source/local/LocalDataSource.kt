package com.example.githubusersearch.core.data.source.local

import com.example.githubusersearch.core.data.source.local.entity.FavoriteUser
import com.example.githubusersearch.core.data.source.local.room.FavoriteUserDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val favoriteUserDao: FavoriteUserDao) {
    fun getFavoriteUser(): Flow<List<FavoriteUser>> = favoriteUserDao.getFavoriteUsers()

    fun checkUser(id: Int): Flow<FavoriteUser?> = favoriteUserDao.checkUser(id)

    suspend fun addFavoriteUser(favoriteUser: FavoriteUser) = favoriteUserDao.addFavorite(favoriteUser)

    suspend fun removeFavoriteUser(favoriteUser: FavoriteUser) = favoriteUserDao.removeFavorite(favoriteUser)
}