package com.example.githubusersearch.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.githubusersearch.core.data.source.local.entity.FavoriteUser
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavorite(favoriteUser: FavoriteUser)

    @Delete
    suspend fun removeFavorite(favoriteUser: FavoriteUser)

    @Query("SELECT * FROM FavoriteUser")
    fun getFavoriteUsers(): Flow<List<FavoriteUser>>

    @Query("SELECT * FROM FavoriteUser WHERE id = :id")
    fun checkUser(id: Int): Flow<FavoriteUser>
}