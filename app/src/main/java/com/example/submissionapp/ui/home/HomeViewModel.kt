package com.example.submissionapp.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissionapp.data.remote.network.ApiConfig
import com.example.submissionapp.data.remote.response.StoryResponse
import com.example.submissionapp.data.remote.response.StoryResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel():ViewModel() {

    private val _listStory = MutableLiveData<List<StoryResponseItem>>()
    val listStory: LiveData<List<StoryResponseItem>> = _listStory

    companion object{
        const val  TAG = "HomeViewModel"
    }

    fun getAllStories(token: String?){
        val client = ApiConfig.getApiService().getAllStories(token)
        client.enqueue(object : Callback<StoryResponse>{
            override fun onResponse(call: Call<StoryResponse>, response: Response<StoryResponse>) {
                if(response.isSuccessful){
                    val responseBody = response.body()
//                    _listStory.postValue(responseBody?.listStory)
                    Log.d(TAG, responseBody?.listStory.toString())
                }
            }

            override fun onFailure(call: Call<StoryResponse>, t: Throwable) {
                Log.d(TAG,t.message.toString())
            }

        })
    }
}