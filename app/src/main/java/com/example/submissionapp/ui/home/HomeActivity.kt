package com.example.submissionapp.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionapp.adapter.ListStoryAdapter
import com.example.submissionapp.data.Story
import com.example.submissionapp.data.StoryAdapter
import com.example.submissionapp.data.TokenPreferences
import com.example.submissionapp.data.remote.network.ApiConfig
import com.example.submissionapp.data.remote.response.StoryResponse
import com.example.submissionapp.data.remote.response.StoryResponseItem
import com.example.submissionapp.databinding.ActivityHomeBinding
import com.example.submissionapp.ui.add_story.AddStoryActivity
import com.example.submissionapp.ui.detail_story.DetailStoryActivity
import com.example.submissionapp.ui.login.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var tokenPreferences: TokenPreferences
    private lateinit var listStory : List<StoryResponseItem>

    companion object{
        private const val TAG = "HomeActivity"
        private var TOKEN_KEY = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tokenPreferences = TokenPreferences(this)
        setupViewModel()
        validatePreferences()
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
            setStoryData(listStory)
        }
    }

    private fun validatePreferences() {
        TOKEN_KEY = tokenPreferences.getToken().toString()
        Log.i(TAG,"Validate preferences dipanggil. token adalah : ${TOKEN_KEY}")
        if (TOKEN_KEY == null) {
            Log.d(TAG, "User belum login")
            val intent = Intent(this@HomeActivity, LoginActivity::class.java)
            startActivity(intent)
        }
        else{
            setStoryData(listStory)
        }
    }

    private fun setStoryData(listStory: List<StoryResponseItem>) {
        val adapter = ListStoryAdapter(listStory)
        binding.rvStory.adapter = adapter
        binding.rvStory.layoutManager = LinearLayoutManager(this)
        adapter.setItemClickCallback(object : ListStoryAdapter.OnItemClickCallback {
            override fun onItemClicked(data: StoryResponseItem) {
                val intent = Intent(this@HomeActivity, DetailStoryActivity::class.java)
                intent.putExtra(DetailStoryActivity.KEY_USER, data)
                startActivity(intent)
            }
        })
    }

//    private fun getStory(){
//        val client = ApiConfig.getApiService().getAllStories(TOKEN_KEY)
//        client.enqueue(object : Callback<StoryResponse>{
//            override fun onResponse(call: Call<StoryResponse>, response: Response<StoryResponse>) {
//                if(response.isSuccessful){
//                    val responseBody = response.body()
//                    if(responseBody != null){
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<StoryResponse>, t: Throwable) {
//                TODO("Not yet implemented")
//            }
//
//        })
//    }
}