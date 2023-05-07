package com.example.submissionapp.data.remote.network

import androidx.viewbinding.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object{
        fun getApiService(): ApiService {
            val loggingInterceptor = if(BuildConfig.DEBUG) {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
            }
//            val authInterceptor = Interceptor { chain ->
//                val req = chain.request()
//                val requestHeaders = req.newBuilder()
//                    .addHeader("Authorization", "BuildConfig.KEY")
//                    .build()
//                chain.proceed(requestHeaders)
//            }
            val client = OkHttpClient.Builder()
//                .addInterceptor(authInterceptor)
                .addInterceptor(loggingInterceptor)
                .build()
            val retrofit = Retrofit.Builder()
                // Anda juga dapat menggunakan class ini untuk keperluan API lain dengan mengganti kode pada base URL dan nama service-nya
                .baseUrl("https://story-api.dicoding.dev/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}