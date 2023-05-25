package com.example.submissionapp.data.paging

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.*
import com.example.submissionapp.data.StoryRemoteMediator
import com.example.submissionapp.data.TokenPreferences
import com.example.submissionapp.data.room.StoryDatabase
import com.example.submissionapp.data.remote.network.ApiService
import com.example.submissionapp.data.remote.response.StoryResponseItem

class StoryRepository(
    private val storyDatabase: StoryDatabase,
    private val apiService: ApiService,
    private val tokenPreferences: TokenPreferences
    ) {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    @OptIn(ExperimentalPagingApi::class)
    fun getStories(): LiveData<PagingData<StoryResponseItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            remoteMediator = StoryRemoteMediator(storyDatabase,apiService,tokenPreferences),
            pagingSourceFactory = {
                storyDatabase.storyDao().getAllStory()
            }
        ).liveData
    }
}