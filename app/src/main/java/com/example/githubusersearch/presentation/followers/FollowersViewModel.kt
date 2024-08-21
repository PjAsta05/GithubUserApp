package com.example.githubusersearch.presentation.followers

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
class FollowersViewModel @Inject constructor(private val githubUserUseCase: GithubUserUseCase) : ViewModel() {
    private val _listFollowers = MutableLiveData<ArrayList<User>>()
    val listFollowers: LiveData<ArrayList<User>> = _listFollowers

    fun setListFollowers(username: String) {
        viewModelScope.launch {
            githubUserUseCase.getFollowers(username).collect { users ->
                _listFollowers.value = users?.let {
                    ArrayList(it)
                }
            }
        }
    }
}