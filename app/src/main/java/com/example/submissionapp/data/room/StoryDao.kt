package com.example.submissionapp.data.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.submissionapp.data.remote.response.StoryResponseItem


@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStory(vararg story : StoryResponseItem)

    @Query("SELECT * FROM story_database")
    fun getAllStory(): PagingSource<Int, StoryResponseItem>

    @Query("DELETE FROM story_database")
    fun deleteAllStory()
}