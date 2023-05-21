package com.example.submissionapp.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionapp.R
import com.example.submissionapp.adapter.ListPagingAdapter
import com.example.submissionapp.adapter.ListStoryAdapter
import com.example.submissionapp.adapter.LoadingStateAdapter
import com.example.submissionapp.data.Story
import com.example.submissionapp.data.TokenPreferences
import com.example.submissionapp.data.database.StoryDatabase
import com.example.submissionapp.data.paging.StoryRepository
import com.example.submissionapp.data.remote.network.ApiService
import com.example.submissionapp.data.remote.response.StoryResponseItem
import com.example.submissionapp.databinding.ActivityHomeBinding
import com.example.submissionapp.ui.add_story.AddStoryActivity
import com.example.submissionapp.ui.detail_story.DetailStoryActivity
import com.example.submissionapp.ui.login.LoginActivity
import com.example.submissionapp.ui.map.MapsActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var tokenPreferences: TokenPreferences
    private lateinit var storyDatabase: StoryDatabase
    private lateinit var apiService: ApiService
    private lateinit var adapter : ListPagingAdapter


    companion object{
        private const val TAG = "HomeActivity"
        private var TOKEN_KEY = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setTitle("Homepage")

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val context = applicationContext
        tokenPreferences = TokenPreferences(this)
        storyDatabase = StoryDatabase.getDatabase(context)

        validatePreferences()
        setupViewModel()
        setStoryData()
        fabAction()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
         return when (item.itemId) {
             R.id.menu_logout -> {
                 tokenPreferences.deleteToken()
                 Log.d(TAG, "token sudah dihapus")
                 Log.d(TAG, "token saat ini adalah : $TOKEN_KEY")
                 startActivity(Intent(this, LoginActivity::class.java))
                 finish()
                 true
             }
             R.id.menu_map ->{
                 val intent = Intent(this@HomeActivity, MapsActivity::class.java)
                 startActivity(intent)
                 finish()
                true
             }else ->{
                return super.onOptionsItemSelected(item)
            }
        }
    }

    private fun fabAction(){
        binding.fabAddStory.setOnClickListener{
            val intent = Intent(this@HomeActivity, AddStoryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupViewModel() {
        val repository = StoryRepository(storyDatabase,apiService,tokenPreferences)
        val viewModelFactory = MyViewModelFactory(repository)
        val viewmodel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
        viewmodel.isLoading.observe(this){showLoading(it)}
        viewmodel.story.observe(this){
            adapter.submitData(lifecycle,it)
        }
    }

    private fun validatePreferences() {
        TOKEN_KEY = tokenPreferences.getToken().toString()
        Log.i(TAG,"Validate preferences dipanggil. token adalah : ${TOKEN_KEY}")
        if (TOKEN_KEY == "") {
            Log.d(TAG, "User belum login")
            val intent = Intent(this@HomeActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setStoryData() {
//        val adapter = ListStoryAdapter(listStory)
//        binding.rvStory.adapter = adapter
//        binding.rvStory.layoutManager = LinearLayoutManager(this)
//        Log.d(TAG, "Recycler telah dipanggil")
//        adapter.setItemClickCallback(object : ListStoryAdapter.OnItemClickCallback {
//            override fun onItemClicked(data: StoryResponseItem) {
//                val intent = Intent(this@HomeActivity, DetailStoryActivity::class.java)
//                intent.putExtra(DetailStoryActivity.ID_USER, data.id)
//                startActivity(intent)
//            }
//        })

        binding.apply {
            rvStory.layoutManager = LinearLayoutManager(this@HomeActivity)
            rvStory.setHasFixedSize(true)
            rvStory.adapter = adapter.withLoadStateFooter(
                footer = LoadingStateAdapter{
                    adapter.retry()
                }
            )
            binding.rvStory.layoutManager = LinearLayoutManager(this@HomeActivity)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
    private fun showLoading(isLoading : Boolean){
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}