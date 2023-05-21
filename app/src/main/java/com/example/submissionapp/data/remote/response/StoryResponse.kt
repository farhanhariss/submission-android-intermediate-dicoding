package com.example.submissionapp.data.remote.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Parcelize
data class StoryResponse(
	@field:SerializedName("listStory")
	val listStory: List<StoryResponseItem>,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String

) : Parcelable

data class DetailStoryResponse(
	@SerializedName("error")
	val error: Boolean,

	@SerializedName("message")
	val message: String,

	@SerializedName("story")
	val story: StoryResponseItem
)

@Parcelize
@Entity(tableName = "story")
data class StoryResponseItem(

	@field:SerializedName("photoUrl")
	val photoUrl: String,

	@field:SerializedName("name")
	val name: String,

	@PrimaryKey
	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("lon")
	val lon: Double,

	@field:SerializedName("lat")
	val lat: Double

) : Parcelable


data class ListStoryResponse(
	val error : Boolean,
	val listStories : List<StoryResponseItem>,
	val message : String
)