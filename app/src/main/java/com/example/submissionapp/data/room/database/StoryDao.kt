package com.example.submissionapp.data.room.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.submissionapp.data.remote.response.StoryResponseItem


@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertStory(story : StoryResponseItem)

    @Query("SELECT * FROM userStories")
    fun getAllStory(): LiveData<List<StoryResponseItem>>

    @Delete
    fun deleteAllStory(story: StoryResponseItem)
}