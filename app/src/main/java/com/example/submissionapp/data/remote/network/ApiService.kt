package com.example.submissionapp.data.remote.network

import com.example.submissionapp.data.remote.response.FileUploadResponse
import com.example.submissionapp.data.remote.response.LoginResponse
import com.example.submissionapp.data.remote.response.RegisterResponse
import com.example.submissionapp.data.remote.response.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    fun registerUser(
        @Field("name") name : String,
        @Field("email") email : String,
        @Field("password") password : String
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @GET("stories")
    fun getAllStories(
        @Header("Authorization") token: String?,
    ): Call<StoryResponse>

    @Multipart
    @POST("stories")
    fun uploadStory(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Field("description") description: RequestBody,
    ) : Call<FileUploadResponse>


}