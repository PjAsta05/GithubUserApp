package com.example.githubusersearch.presentation.followings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubusersearch.core.domain.model.User
import com.example.githubusersearch.core.domain.usecase.GithubUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowingViewModel @Inject constructor(private val githubUserUseCase: GithubUserUseCase) : ViewModel() {
    private val _listFollowing = MutableLiveData<ArrayList<User>>()
    val listFollowing: LiveData<ArrayList<User>> = _listFollowing

    fun setListFollowing(username: String) {
        viewModelScope.launch {
            githubUserUseCase.getFollowing(username).collect { users ->
                _listFollowing.value = users?.let {
                    ArrayList(it)
                }
            }
        }
    }
}