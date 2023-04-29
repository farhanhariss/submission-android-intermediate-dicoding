package com.example.submissionapp.data.room.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "userStories")
data class StoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val name: String,

    val description: String,

    @ColumnInfo(name = "photoUrl")
    val photoUrl: String,

    @ColumnInfo(name = "createdAt")
    val createdAt: String,

    val lat : Double?,

    val lon : Double?

) : Parcelable



