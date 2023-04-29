package com.example.submissionapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Story (
    val id: String,
    val username : String,
    val photo : String,
    val description : String
    ) : Parcelable