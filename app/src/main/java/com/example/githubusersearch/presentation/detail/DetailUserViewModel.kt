package com.example.githubusersearch.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubusersearch.core.domain.model.DetailUser
import com.example.githubusersearch.core.domain.model.Favorite
import com.example.githubusersearch.core.domain.usecase.GithubUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailUserViewModel @Inject constructor(private val githubUserUseCase: GithubUserUseCase) : ViewModel() {
    private val _user = MutableLiveData<DetailUser>()
    val user: LiveData<DetailUser> = _user
    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    fun setUserDetail(username: String) {
        viewModelScope.launch {
            githubUserUseCase.getUserDetail(username).collect { user ->
                _user.value = user?.let {
                    DetailUser(it.login, it.id, it.avatarUrl, it.name, it.followers, it.following)
                }
            }
        }
    }

    fun addFavorite(favoriteUser: Favorite) {
        viewModelScope.launch {
            githubUserUseCase.addFavoriteUser(favoriteUser)
        }

    }

    fun removeFavorite(favoriteUser: Favorite) {
        viewModelScope.launch {
            githubUserUseCase.removeFavoriteUser(favoriteUser)
        }
    }

    fun checkUser(id: Int) {
        viewModelScope.launch {
            githubUserUseCase.checkUser(id).collect { user ->
                _isFavorite.value = user != null
            }
        }
    }
}