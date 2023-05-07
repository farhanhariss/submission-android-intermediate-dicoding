package com.example.submissionapp.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionapp.adapter.ListStoryAdapter
import com.example.submissionapp.data.TokenPreferences
import com.example.submissionapp.data.remote.response.StoryResponseItem
import com.example.submissionapp.databinding.ActivityHomeBinding
import com.example.submissionapp.ui.add_story.AddStoryActivity
import com.example.submissionapp.ui.detail_story.DetailStoryActivity
import com.example.submissionapp.ui.login.LoginActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var tokenPreferences: TokenPreferences

    companion object{
        private const val TAG = "HomeActivity"
        private var TOKEN_KEY = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tokenPreferences = TokenPreferences(this)
        validatePreferences()
        setupViewModel()
        fabAction()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    private fun fabAction(){
        binding.fabAddStory.setOnClickListener{
            val intent = Intent(this@HomeActivity, AddStoryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupViewModel() {
        val viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.getAllStories(tokenPreferences.getToken())
        viewModel.listStory.observe(this){
            listStory ->
            Log.d(TAG, "Data list story : $listStory")
            setStoryData(listStory)
        }
        Log.d(TAG, "SetupViewModel dipanggil")
    }

    private fun validatePreferences() {
        TOKEN_KEY = tokenPreferences.getToken().toString()
        Log.i(TAG,"Validate preferences dipanggil. token adalah : ${TOKEN_KEY}")
        if (TOKEN_KEY == null) {
            Log.d(TAG, "User belum login")
            val intent = Intent(this@HomeActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setStoryData(listStory: List<StoryResponseItem>) {
        val adapter = ListStoryAdapter(listStory)
        binding.rvStory.adapter = adapter
        binding.rvStory.layoutManager = LinearLayoutManager(this)
        Log.d(TAG, "Recycler telah dipanggil")
        adapter.setItemClickCallback(object : ListStoryAdapter.OnItemClickCallback {
            override fun onItemClicked(data: StoryResponseItem) {
                val intent = Intent(this@HomeActivity, DetailStoryActivity::class.java)
                intent.putExtra(DetailStoryActivity.ID_USER, data.id)
                startActivity(intent)
            }
        })
    }
}