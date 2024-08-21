package com.example.githubusersearch.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.githubusersearch.core.data.source.local.entity.FavoriteUser

@Database(entities = [FavoriteUser::class], version = 1, exportSchema = false)
abstract class FavoriteUserRoom : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteUserDao
}