package com.example.githubusersearch.core.domain.usecase

import com.example.githubusersearch.core.domain.model.Favorite
import com.example.githubusersearch.core.domain.repository.IGithubUserRepository
import javax.inject.Inject

class GithubUserInteractor @Inject constructor(private val githubUserRepository: IGithubUserRepository): GithubUserUseCase {
    override suspend fun getSearchUsers(query: String) = githubUserRepository.getSearchUsers(query)
    override suspend fun getUserDetail(username: String) = githubUserRepository.getUserDetail(username)
    override suspend fun getFollowers(username: String) = githubUserRepository.getFollowers(username)
    override suspend fun getFollowing(username: String) = githubUserRepository.getFollowing(username)
    override  fun getFavoriteUsers() = githubUserRepository.getFavoriteUsers()
    override fun checkUser(id: Int) = githubUserRepository.checkUser(id)
    override suspend fun addFavoriteUser(favoriteUser: Favorite) = githubUserRepository.addFavoriteUser(favoriteUser)
    override suspend fun removeFavoriteUser(favoriteUser: Favorite) = githubUserRepository.removeFavoriteUser(favoriteUser)
}