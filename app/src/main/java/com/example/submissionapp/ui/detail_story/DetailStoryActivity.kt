package com.example.submissionapp.ui.detail_story

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.submissionapp.R

class DetailStoryActivity : AppCompatActivity() {

    companion object{
        const val KEY_USER = "key_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_story)
    }
}