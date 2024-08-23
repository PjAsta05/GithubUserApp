package com.example.githubusersearch.core.data

import android.util.Log
import com.example.githubusersearch.core.data.source.local.LocalDataSource
import com.example.githubusersearch.core.data.source.remote.RemoteDataSource
import com.example.githubusersearch.core.domain.model.DetailUser
import com.example.githubusersearch.core.domain.model.Favorite
import com.example.githubusersearch.core.domain.model.User
import com.example.githubusersearch.core.domain.repository.IGithubUserRepository
import com.example.githubusersearch.core.util.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubUserRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IGithubUserRepository {
    override suspend fun getSearchUsers(query: String): Flow<List<User>?> {
        return remoteDataSource.getSearchUsers(query).map { userResponse ->
            userResponse?.let { DataMapper.mapUserResponseToDomain(it) } ?: emptyList()
        }
    }

    override suspend fun getUserDetail(username: String): Flow<DetailUser?> {
        return remoteDataSource.getUserDetail(username).map { detailUserResponse ->
            detailUserResponse?.let { DataMapper.mapDetailUserResponseToDomain(it) }
        }
    }

    override suspend fun getFollowers(username: String): Flow<List<User>?> {
        return remoteDataSource.getFollowers(username).map { userResponse ->
            userResponse?.let { DataMapper.mapFollowListResponseToDomain(it) }
        }
    }

    override suspend fun getFollowing(username: String): Flow<List<User>?> {
        return remoteDataSource.getFollowing(username).map {userResponse ->
            userResponse?.let { DataMapper.mapFollowListResponseToDomain(it) }
        }
    }

    override fun getFavoriteUsers(): Flow<List<Favorite>> {
        return localDataSource.getFavoriteUser().map { favoriteUsers ->
            DataMapper.mapFavoriteListToDomain(favoriteUsers)
        }
    }

    override fun checkUser(id: Int): Flow<Favorite?> {
        return localDataSource.checkUser(id).map { favoriteUser ->
            favoriteUser?.let { DataMapper.mapFavoriteToDomain(it) }
        }
    }

    override suspend fun addFavoriteUser(favoriteUser: Favorite) {
        val favorite = DataMapper.mapFavoriteToData(favoriteUser)
        return localDataSource.addFavoriteUser(favorite)
    }

    override suspend fun removeFavoriteUser(favoriteUser: Favorite) {
        val favorite = DataMapper.mapFavoriteToData(favoriteUser)
        return localDataSource.removeFavoriteUser(favorite)
    }
}