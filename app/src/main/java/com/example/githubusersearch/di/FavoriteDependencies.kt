package com.example.githubusersearch.di

import com.example.githubusersearch.core.domain.usecase.GithubUserUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteDependencies {
    fun githubUserUseCase(): GithubUserUseCase
}