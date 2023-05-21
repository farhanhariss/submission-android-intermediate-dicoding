package com.example.submissionapp.ui.detail_story

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissionapp.data.Story
import com.example.submissionapp.data.remote.network.ApiConfig
import com.example.submissionapp.data.remote.response.DetailStoryResponse
import com.example.submissionapp.data.remote.response.StoryResponse
import com.example.submissionapp.data.remote.response.StoryResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    private val _detailStory = MutableLiveData<StoryResponseItem>()
    val detailStory : LiveData<StoryResponseItem> = _detailStory

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "DetailViewModel"
    }

    fun getDetailStory(token:String?, id : String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailStories("Bearer $token", id)
        client.enqueue(object : Callback<DetailStoryResponse>{
            override fun onResponse(
                call: Call<DetailStoryResponse>,
                response: Response<DetailStoryResponse>) {
                _isLoading.value = false
                val responseBody = response.body()
                if(response.isSuccessful){
                    _detailStory.value = responseBody?.story
                }else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<DetailStoryResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "OnFailure : ${t.message}")
            }

        })
    }
}