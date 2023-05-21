package com.example.submissionapp.ui.map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submissionapp.data.TokenPreferences
import com.example.submissionapp.data.remote.network.ApiConfig
import com.example.submissionapp.data.remote.network.ApiService
import com.example.submissionapp.data.remote.response.StoryResponse
import com.example.submissionapp.data.remote.response.StoryResponseItem
import com.example.submissionapp.ui.detail_story.DetailViewModel
import com.example.submissionapp.ui.home.HomeViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsViewModel : ViewModel() {

    private val _listStory = MutableLiveData<List<StoryResponseItem>>()
    val listStory: LiveData<List<StoryResponseItem>> = _listStory

    fun getStories(token: String?){

            val client = ApiConfig.getApiService().getAllStoriesAndLocation("Bearer $token")
            client.enqueue(object : Callback<StoryResponse>{
                override fun onResponse(call: Call<StoryResponse>, response: Response<StoryResponse>) {
                    if (response.isSuccessful){
                        val responseBody = response.body()
                        _listStory.postValue(responseBody?.listStory)
                    }else {
                        Log.e("MapsViewModel", "onFailure: ${response.message()}")
                    }
                }
                override fun onFailure(call: Call<StoryResponse>, t: Throwable) {
                    Log.d("MapsViewModel",t.message.toString())
                }

            })
        }
    }
