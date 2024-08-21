package com.example.githubusersearch.core.di

import android.content.Context
import androidx.room.Room
import com.example.githubusersearch.core.data.source.local.room.FavoriteUserDao
import com.example.githubusersearch.core.data.source.local.room.FavoriteUserRoom
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    private val passphrase: ByteArray = SQLiteDatabase.getBytes("githubUser".toCharArray())
    val factory = SupportFactory(passphrase)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): FavoriteUserRoom = Room.databaseBuilder(
        context,
        FavoriteUserRoom::class.java, "favorite_user_database"
    ).fallbackToDestructiveMigration()
        .openHelperFactory(factory)
        .build()

    @Provides
    fun provideFavoriteUserDao(database: FavoriteUserRoom): FavoriteUserDao = database.favoriteDao()
}