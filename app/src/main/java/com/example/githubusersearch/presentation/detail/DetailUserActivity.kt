package com.example.githubusersearch.presentation.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.SpannableStringBuilder
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.text.bold
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.githubusersearch.R
import com.example.githubusersearch.core.domain.model.Favorite
import com.example.githubusersearch.databinding.ActivityDetailUserBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private val viewModel: DetailUserViewModel by viewModels()
    private lateinit var viewPager: ViewPager
    private var isDataLoaded = false
    private var favorite = false
    private var favoriteUser: Favorite? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewPager = findViewById(R.id.viewPager)

        window.decorView.setOnApplyWindowInsetsListener { _, insets ->
            val orientation = resources.configuration.orientation
            setViewPagerHeight(orientation)
            insets
        }

        val username = intent.getStringExtra(EXTRA_USER)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val bundle = Bundle()
        bundle.putString(EXTRA_USER, username)

        showLoading(true)
        if (username != null) {
            Handler(Looper.getMainLooper()).postDelayed({
                viewModel.setUserDetail(username)
            }, 1000L)
        }

        viewModel.user.observe(this) {
            if (!isDataLoaded) {
                showLoading(false)
                with(binding) {
                    Glide.with(this@DetailUserActivity)
                        .load(it.avatarUrl)
                        .centerCrop()
                        .into(imgAvatar)
                    tvName.text = it.name
                    tvUsername.text = it.login
                    tvId.text = "ID ${it.id}"
                    tvFollowers.text = SpannableStringBuilder()
                        .bold { append("${it.followers}") }
                        .append(" Followers")
                    tvFollowing.text = SpannableStringBuilder()
                        .bold { append("${it.following}") }
                        .append(" Following")
                }
                binding.favoriteButton.visibility = View.VISIBLE
                binding.shareButton.visibility = View.VISIBLE

                favoriteUser = Favorite(
                    it.id,
                    it.login,
                    it.avatarUrl
                )

                val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, bundle)
                with(binding) {
                    viewPager.adapter = sectionPagerAdapter
                    tabLayout.setupWithViewPager(viewPager)
                }
                isDataLoaded = true
            }
        }

        viewModel.checkUser(id)
        isFavorite()
        favoriteButton()
        shareUser()
    }

    private fun shareUser() {
        val username = intent.getStringExtra(EXTRA_USER)
        binding.shareButton.setOnClickListener {
            val intent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Check this GitHub Profile: https://github.com/$username")
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(intent, null)
            startActivity(shareIntent)
        }
    }

    private fun favoriteButton() {
        binding.favoriteButton.setOnClickListener {
            favoriteUser?.let {
                if (binding.favoriteButton.isChecked && !favorite) {
                    viewModel.addFavorite(it)
                    favorite = true
                    Toast.makeText(this, "Added to favorite", Toast.LENGTH_SHORT).show()
                } else if (!binding.favoriteButton.isChecked && favorite) {
                    viewModel.removeFavorite(it)
                    favorite = false
                    Toast.makeText(this, "Removed from favorite", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun isFavorite() {
        viewModel.isFavorite.observe(this) {
            if (it == true) {
                favorite = true
                updateFavoriteButton()
            }
        }
    }

    private fun updateFavoriteButton() {
        binding.favoriteButton.isChecked = favorite
    }

    private fun setViewPagerHeight(orientation: Int) {
        val params = viewPager.layoutParams
        val screenHeight = resources.displayMetrics.heightPixels

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            params.height = screenHeight
            viewPager.layoutParams = params
        }
    }

    private fun showLoading(isLoading: Boolean){
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object{
        const val EXTRA_USER = "extra_user"
        const val EXTRA_ID = "extra_id"
    }
}
