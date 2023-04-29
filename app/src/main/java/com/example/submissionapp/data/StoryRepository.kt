package com.example.submissionapp.data

import androidx.lifecycle.LiveData
import com.example.submissionapp.data.remote.network.ApiService
import com.example.submissionapp.data.remote.response.StoryResponseItem
import com.example.submissionapp.data.room.database.StoryDao
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class StoryRepository(
    private val storyDao: StoryDao,
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    ) {
    private fun getAllStory(): LiveData<List<StoryResponseItem>> {
        return storyDao.getAllStory()
    }

    private fun deleteAllStory(story: StoryResponseItem){
        return storyDao.deleteAllStory(story)
    }

    private fun insertStory(story: StoryResponseItem){
        return storyDao.insertStory(story)
    }


}