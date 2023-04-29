package com.example.submissionapp.ui.register

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.submissionapp.data.remote.network.ApiConfig
import com.example.submissionapp.data.remote.response.RegisterResponse
import com.example.submissionapp.ui.home.HomeActivity
import com.example.submissionapp.ui.login.LoginActivity
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {

    companion object {
        const val TAG = "RegisterViewModel"
    }

    fun registerUser(name: String, username: String, password: String, activity: RegisterActivity){
        val client = ApiConfig.getApiService().registerUser(name, username, password)
        client.enqueue(object : Callback<RegisterResponse>{
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful){
                    var responseBody = response.body()
                    if (responseBody != null){
                        Toast.makeText(activity, responseBody.message, Toast.LENGTH_SHORT).show()
                        activity.finish()
                        val intent = Intent(activity,LoginActivity::class.java)
                        activity.startActivity(intent)
                        Log.e(TAG,"Register berhasil")
                    }
                } else{
                    Log.e(TAG,"Register tidak sukses")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.e(TAG,"Register gagal")
            }
        })
    }

}