package com.example.githubusersearch.presentation.home

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
class MainViewModel @Inject constructor (private val githubUserUseCase: GithubUserUseCase) : ViewModel() {
    private val _listUsers = MutableLiveData<List<User>?>()
    val listUser: LiveData<List<User>?> get() = _listUsers
    private val _query = MutableLiveData<String?>()
    var query: LiveData<String?> = _query

    fun setSearchUsers(query: String) {
        viewModelScope.launch {
            githubUserUseCase.getSearchUsers(query).collect { users ->
                _listUsers.value = users
            }
        }
        _query.value = query
    }

    fun clearQuery() {
        _query.value = null
    }
}