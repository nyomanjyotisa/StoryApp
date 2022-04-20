package id.jyotisa.storyapp.di

import android.content.Context
import id.jyotisa.storyapp.api.RetrofitConfig
import id.jyotisa.storyapp.data.repository.AuthRepository
import id.jyotisa.storyapp.data.repository.StoryRepository
import id.jyotisa.storyapp.database.StoryDatabase

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val database = StoryDatabase.getDatabase(context)
        val apiService = RetrofitConfig.getApiService()
        return StoryRepository(database, apiService)
    }

    fun provideAuthRepository(context: Context): AuthRepository {
        val apiService = RetrofitConfig.getApiService()
        return AuthRepository(apiService)
    }
}
