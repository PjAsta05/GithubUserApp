package com.example.githubusersearch.presentation.followings

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubusersearch.R
import com.example.githubusersearch.databinding.FragmentFollowBinding
import com.example.githubusersearch.core.domain.model.User
import com.example.githubusersearch.presentation.detail.DetailUserActivity
import com.example.githubusersearch.core.ui.UserAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowingFragment: Fragment(R.layout.fragment_follow) {
    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FollowingViewModel by viewModels()
    private lateinit var adapter: UserAdapter
    private lateinit var username: String
    private var isDataLoaded = false

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        username = arguments?.getString(DetailUserActivity.EXTRA_USER).toString()
        _binding = FragmentFollowBinding.bind(view)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                val intent = Intent(requireContext(), DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USER, data.login)
                    it.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                }
                startActivity(intent)
            }
        })
        with(binding) {
            rvUsers.setHasFixedSize(true)
            rvUsers.layoutManager = LinearLayoutManager(activity)
            rvUsers.adapter = adapter
        }

        showLoading(true)
        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.setListFollowing(username)
        }, 1000L)
        viewModel.listFollowing.observe(viewLifecycleOwner) {
            if (!isDataLoaded) {
                if (it.isNotEmpty()){
                    adapter.submitList(it)
                    showLoading(false)
                    noUsers(false)
                }else {
                    showLoading(false)
                    noUsers(true)
                }
                isDataLoaded = true
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun noUsers(state: Boolean) {
        binding.tvNoUsers.visibility = if (state) View.VISIBLE else View.GONE
    }
}