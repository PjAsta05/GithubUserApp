package com.example.githubusersearch.favorites.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubusersearch.core.domain.usecase.GithubUserUseCase
import com.example.githubusersearch.favorites.FavoriteViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val githubUserUseCase: GithubUserUseCase): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(githubUserUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}