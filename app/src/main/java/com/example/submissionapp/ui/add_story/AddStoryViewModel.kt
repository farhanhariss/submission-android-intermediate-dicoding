package com.example.submissionapp.ui.add_story

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.submissionapp.data.remote.network.ApiConfig
import com.example.submissionapp.data.remote.response.FileUploadResponse
import retrofit2.Callback
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response

class AddStoryViewModel : ViewModel() {
    companion object{
        const val TAG = "AddStoryViewModel"
    }


    private fun uploadStory(token: String,photoMultipart:MultipartBody.Part,description:RequestBody){
        val client = ApiConfig.getApiService().uploadStory(token,photoMultipart,description)
        client.enqueue(object : Callback<FileUploadResponse>{
            override fun onResponse(
                call: Call<FileUploadResponse>,
                response: Response<FileUploadResponse>
            ) {
                val responseBody = response.body()
                if (responseBody != null && !responseBody.error){
                    Log.d(TAG,"Upload foto berhasil")
                }else{
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<FileUploadResponse>, t: Throwable) {
                Log.e(TAG, "OnFailure : ${t.message}")

            }

        })


    }
}