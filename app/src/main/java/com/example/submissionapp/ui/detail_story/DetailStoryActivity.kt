package com.example.submissionapp.ui.detail_story

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.submissionapp.data.TokenPreferences
import com.example.submissionapp.databinding.ActivityDetailStoryBinding

class DetailStoryActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailStoryBinding
    private lateinit var detailViewModel : DetailViewModel
    private lateinit var tokenPreferences: TokenPreferences

    companion object{
        const val ID_USER = "id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tokenPreferences = TokenPreferences(this)

        supportActionBar?.setTitle("Detail Story")

        val getId = intent.getStringExtra(ID_USER)
        if (getId == null){
            finish()
            return
        }
        setDetailStory(getId)
    }

    private fun setDetailStory(id:String){
        //obtain viewmodel
        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        detailViewModel.isLoading.observe(this){showLoading(it)}
        detailViewModel.getDetailStory(tokenPreferences.getToken(),id)
        detailViewModel.detailStory.observe(this){DetailStoryResponse ->
            binding.tvUsername.text = DetailStoryResponse.name
            binding.tvTextStory.text = DetailStoryResponse.description
            Glide.with(this)
                .load(DetailStoryResponse.photoUrl)
                .into(binding.imgDetailStory)
        }
    }

    private fun showLoading(isLoading : Boolean){
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}