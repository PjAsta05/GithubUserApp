package com.example.githubusersearch.core.di

import com.example.githubusersearch.core.data.GithubUserRepository
import com.example.githubusersearch.core.domain.repository.IGithubUserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideRepository(githubUserRepository: GithubUserRepository): IGithubUserRepository
}