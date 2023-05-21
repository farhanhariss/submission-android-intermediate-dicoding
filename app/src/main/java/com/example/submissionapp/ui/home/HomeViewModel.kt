package com.example.submissionapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.submissionapp.data.paging.StoryRepository
import com.example.submissionapp.data.remote.response.StoryResponseItem

class HomeViewModel (repository: StoryRepository) :ViewModel() {
    val isLoading: LiveData<Boolean> = repository.isLoading
    val story: LiveData<PagingData<StoryResponseItem>> = repository.getStories().cachedIn(viewModelScope)


//    private val _listStory = MutableLiveData<List<StoryResponseItem>>()
//    val listStory: LiveData<List<StoryResponseItem>> = _listStory


//    companion object{
//        const val  TAG = "HomeViewModel"
//    }

//    fun getAllStories(token: String?){
//        _isLoading.value = true
//        val client = ApiConfig.getApiService().getPagingStory("Bearer $token",)
//        Log.d(TAG, "hasil variable client : $client")
//        client.enqueue(object : Callback<StoryResponse>{
//            override fun onResponse(
//                call: Call<StoryResponse>,
//                response: Response<StoryResponse>) {
//                _isLoading.value = false
//                if(response.isSuccessful){
//                    val responseBody = response.body()
//                    Log.d(TAG, "hasil variable responseBody : $responseBody")
//                    _listStory.postValue(responseBody?.listStory)
//                    Log.d(TAG, responseBody?.listStory.toString())
//                } else {
//                    Log.e(TAG, "onFailure: ${response.message()}")
//                }
//            }
//
//            override fun onFailure(call: Call<StoryResponse>, t: Throwable) {
//                _isLoading.value = false
//                Log.d(TAG,t.message.toString())
//            }
//
//        })
//    }


}

class MyViewModelFactory(private val repository: StoryRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.canonicalName}")
    }
}
