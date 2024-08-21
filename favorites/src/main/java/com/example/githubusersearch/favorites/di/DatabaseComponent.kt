package com.example.githubusersearch.favorites.di

import android.content.Context
import com.example.githubusersearch.di.FavoriteDependencies
import com.example.githubusersearch.favorites.FavoriteActivity
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [FavoriteDependencies::class])
interface DatabaseComponent {
    fun inject(activity: FavoriteActivity)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(favoriteDependencies: FavoriteDependencies): Builder
        fun build(): DatabaseComponent
    }
}