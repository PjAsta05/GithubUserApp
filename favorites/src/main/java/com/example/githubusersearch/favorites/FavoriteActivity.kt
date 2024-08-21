package com.example.githubusersearch.favorites

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubusersearch.core.domain.model.User
import com.example.githubusersearch.core.ui.UserAdapter
import com.example.githubusersearch.di.FavoriteDependencies
import com.example.githubusersearch.favorites.databinding.ActivityFavoriteBinding
import com.example.githubusersearch.favorites.di.DaggerDatabaseComponent
import com.example.githubusersearch.favorites.di.ViewModelFactory
import com.example.githubusersearch.presentation.detail.DetailUserActivity
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: FavoriteViewModel by viewModels { factory }
    private lateinit var adapter: UserAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerDatabaseComponent.builder()
            .context(this)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    applicationContext,
                    FavoriteDependencies::class.java
                )
            )
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val intent = Intent(this@FavoriteActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USER, data.login)
                    it.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                }
                startActivity(intent)
            }
        })

        with(binding) {
            rvUser.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapter
        }

        viewModel.getFavoriteUser()
        viewModel.isUpdate.observe(this) {isUpdate ->
            if (isUpdate){
                showLoading(true)
                Handler(Looper.getMainLooper()).postDelayed({
                    observeLiveData()
                }, 1000L)
            }else {
                observeLiveData()
            }
        }
    }

    private fun observeLiveData() {
        val list = arrayListOf<User>()
        viewModel.favoriteUsers.observe(this) {users ->
            if (users.isNotEmpty()) {
                users.map {
                    val user = User(id = it.id, login = it.login, avatarUrl = it.avatarUrl.toString())
                    list.add(user)
                }
                adapter.submitList(list)
                noUsers(false)
            }else {
                adapter.submitList(emptyList())
                noUsers(true)
            }
            showLoading(false)
        }
    }

    private fun showLoading(isLoading: Boolean){
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun noUsers(isEmpty: Boolean) {
        binding.tvNoUsers.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }
}