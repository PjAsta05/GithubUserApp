package com.example.githubusersearch.core.data.source.remote

import android.util.Log
import com.example.githubusersearch.core.data.source.remote.network.ApiService
import com.example.githubusersearch.core.data.source.remote.response.DetailUserResponse
import com.example.githubusersearch.core.data.source.remote.response.UserData
import com.example.githubusersearch.core.data.source.remote.response.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {
    suspend fun getSearchUsers(query: String): Flow<UserResponse?> {
        return flow {
            try {
                val response = apiService.getSearchUsers(query)
                if (response.isSuccessful) {
                    emit(response.body())
                } else {
                    emit(null)
                }
            } catch (e: Exception) {
                Log.e("RemoteDataSource", e.toString())
                emit(null)
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getUserDetail(username: String): Flow<DetailUserResponse?> {
        return flow {
            try {
                val response = apiService.getUserDetail(username)
                if (response.isSuccessful) {
                    emit(response.body())
                } else {
                    emit(null)
                }
            } catch (e: Exception) {
                Log.e("RemoteDataSource", e.toString())
                emit(null)
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getFollowers(username: String): Flow<List<UserData>?> {
        return flow {
            try {
                val response = apiService.getFollowers(username)
                if (response.isSuccessful) {
                    emit(response.body())
                } else {
                    emit(null)
                }
            } catch (e: Exception) {
                Log.e("RemoteDataSource", e.toString())
                emit(null)
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getFollowing(username: String): Flow<List<UserData>?> {
        return flow {
            try {
                val response = apiService.getFollowing(username)
                if (response.isSuccessful) {
                    emit(response.body())
                } else {
                    emit(null)
                }
            } catch (e: Exception) {
                Log.e("RemoteDataSource", e.toString())
                emit(null)
            }
        }.flowOn(Dispatchers.IO)
    }
}