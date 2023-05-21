package com.example.submissionapp.ui.login

import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissionapp.data.TokenPreferences
import com.example.submissionapp.data.remote.network.ApiConfig
import com.example.submissionapp.data.remote.response.LoginResponse
import com.example.submissionapp.ui.home.HomeActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel() : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        const val TAG = "LoginViewModel"
    }


    fun checkUserKey(username: String, password:String, activity: LoginActivity,tokenPref : TokenPreferences){
        _isLoading.value = false
        val client = ApiConfig.getApiService().loginUser(username,password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>) {
                _isLoading.value = false
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null){
                        //save token di preferences
                        tokenPref.saveToken(responseBody.loginResult)
                        val intent = Intent(activity,HomeActivity::class.java)
                        activity.startActivity(intent)
                        activity.finish()
                        Log.e(TAG, "Berhasil melakukan login!")
                    }
                }else{
                    Log.e(TAG, "OnFailure : ${response.message()}, Maaf username serta password yang Anda input salah!")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "OnFailure : ${t.message}, No Response")
            }
        })
    }
}