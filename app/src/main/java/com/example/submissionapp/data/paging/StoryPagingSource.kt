package com.example.submissionapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.submissionapp.data.TokenPreferences
import com.example.submissionapp.data.remote.network.ApiService
import com.example.submissionapp.data.remote.response.StoryResponse
import com.example.submissionapp.data.remote.response.StoryResponseItem

class StoryPagingSource (private val tokenPreferences: TokenPreferences, private val apiService: ApiService) : PagingSource<Int, StoryResponseItem>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, StoryResponseItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StoryResponseItem> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val token: String? = tokenPreferences.getToken()
            val responseData = apiService.getPagingStory("Bearer $token",position, params.loadSize)
            LoadResult.Page(
                data = responseData.listStory,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (responseData.listStory.isNullOrEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }
}
