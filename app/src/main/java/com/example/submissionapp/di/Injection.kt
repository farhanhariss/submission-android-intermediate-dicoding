package com.example.submissionapp.di

import android.content.Context
import com.example.submissionapp.data.TokenPreferences
import com.example.submissionapp.data.room.StoryDatabase
import com.example.submissionapp.data.paging.StoryRepository
import com.example.submissionapp.data.remote.network.ApiConfig

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val database = StoryDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        val tokenPreferences = TokenPreferences(context)
        return StoryRepository(database, apiService, tokenPreferences)
    }
}
