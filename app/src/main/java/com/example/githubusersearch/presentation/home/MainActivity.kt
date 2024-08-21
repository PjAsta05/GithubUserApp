package com.example.githubusersearch.presentation.home

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubusersearch.R
import com.example.githubusersearch.databinding.ActivityMainBinding
import com.example.githubusersearch.core.domain.model.User
import com.example.githubusersearch.presentation.detail.DetailUserActivity
import com.example.githubusersearch.core.ui.UserAdapter
import com.example.githubusersearch.theme.SettingPreferences
import com.example.githubusersearch.theme.ThemeModelFactory
import com.example.githubusersearch.theme.ThemeViewModel
import com.example.githubusersearch.theme.dataStore
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: UserAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val themeViewModel = ViewModelProvider(this, ThemeModelFactory(pref))[ThemeViewModel::class.java]

        themeViewModel.getThemeSettings().observe(this) {isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.themeButton.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.themeButton.isChecked = false
            }
        }

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val intent = Intent(this@MainActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USER, data.login)
                    it.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                }
                startActivity(intent)
            }
        })

        if (viewModel.listUser.value.isNullOrEmpty()){
            searchUser("dicoding")
            viewModel.clearQuery()
        }


        with(binding) {
            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapter

            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    clearRecyclerView()
                    noUsers(false)
                    val query = searchView.text.toString()
                    if (query.isNotBlank()) {
                        searchUser(query)
                        setQueryText()
                        searchView.hide()
                    } else {
                        Toast.makeText(this@MainActivity,
                            getString(R.string.please_enter_a_username_to_search_for), Toast.LENGTH_SHORT).show()
                        observeLiveData()
                    }
                    true
                }
        }

        binding.ivFavorite.setOnClickListener {
            val uri = Uri.parse("favorite_users://favorites")
            startActivity(Intent(Intent.ACTION_VIEW, uri))
        }

        binding.themeButton.setOnClickListener {
            if (binding.themeButton.isChecked) {
                themeViewModel.saveThemeSetting(true)
            }else if (!binding.themeButton.isChecked) {
                themeViewModel.saveThemeSetting(false)
            }
        }

        setQueryText()
        observeLiveData()
    }

    private fun setQueryText() {
        viewModel.query.observe(this) {
            if (it != null) {
                binding.apply {
                    searchBar.hint = it
                }
            }
        }
    }

    private fun observeLiveData(){
        viewModel.listUser.observe(this) {
            if (it != null) {
                if (it.isNotEmpty()) {
                    adapter.submitList(it)
                    noUsers(false)
                } else {
                    noUsers(true)
                }
            }
            showLoading(false)
        }
    }

    private fun searchUser(query: String) {
        showLoading(true)
        viewModel.setSearchUsers(query)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun clearRecyclerView() {
        adapter.clearData()
        adapter.notifyDataSetChanged()
    }

    private fun showLoading(isLoading: Boolean){
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun noUsers(isEmpty: Boolean) {
        binding.tvNoUsers.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }
}