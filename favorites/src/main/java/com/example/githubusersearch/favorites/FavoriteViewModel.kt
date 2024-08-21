package com.example.githubusersearch.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.githubusersearch.core.domain.model.Favorite
import com.example.githubusersearch.core.domain.usecase.GithubUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val githubUserUseCase: GithubUserUseCase): ViewModel() {
    private val _favoriteUsers = MutableLiveData<List<Favorite>>()
    val favoriteUsers: LiveData<List<Favorite>> = _favoriteUsers

    private val _isUpdate = MutableLiveData<Boolean>()
    val isUpdate: LiveData<Boolean> = _isUpdate

    fun getFavoriteUser() {
        githubUserUseCase.getFavoriteUsers().asLiveData().observeForever { newList ->
            val previousList = _favoriteUsers.value ?: emptyList()
            if (previousList != newList) {
                _isUpdate.value = true
                _favoriteUsers.value = newList
            } else {
                _isUpdate.value = false
            }
        }
    }
}
