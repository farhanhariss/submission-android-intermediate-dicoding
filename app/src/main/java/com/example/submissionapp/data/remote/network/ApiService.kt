package com.example.submissionapp.data.remote.network

import com.example.submissionapp.data.remote.response.*
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

//    @GET("stories")
//    fun getAllStories(
//        @Header("Authorization") token: String?,
//    ): Call<StoryResponse>

    @GET("stories")
    fun getPagingStory(
        @Header("Authorization") token: String?,
        @Query("page")page: Int,
        @Query("size")size: Int
    ) : ListStoryResponse

    @GET("stories/{id}")
    fun getDetailStories(
        @Header("Authorization") token: String?,
        @Path("id") id: String
    ): Call<DetailStoryResponse>

    @Multipart
    @POST("stories")
    fun uploadStory(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ) : Call<FileUploadResponse>


    @GET("stories?location=1")
    fun getAllStoriesAndLocation(
        @Header("Authorization") token: String
    ) : Call<StoryResponse>


}