package com.example.submissionapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submissionapp.data.remote.response.StoryResponseItem
import com.example.submissionapp.databinding.ItemUserStoryBinding
import com.example.submissionapp.ui.detail_story.DetailStoryActivity

class ListPagingAdapter : PagingDataAdapter<StoryResponseItem, ListPagingAdapter.ViewHolder>(DIFF_CALLBACK){
    override fun onBindViewHolder(holder: ListPagingAdapter.ViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null){
            holder.bind(data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListPagingAdapter.ViewHolder {
        val view = ItemUserStoryBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return ViewHolder(view)
    }

    class ViewHolder(private val binding: ItemUserStoryBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(story: StoryResponseItem){
            binding.apply {
                Glide.with(itemView.context)
                    .load(story.photoUrl)
                    .into(photoStory)

                binding.nameUser.text = story.name

                binding.root.setOnClickListener {
                    binding.root.context.startActivity(
                        Intent(binding.root.context, DetailStoryActivity::class.java).putExtra(DetailStoryActivity.ID_USER, story.id)
                    )
                }
            }
        }
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<StoryResponseItem>() {
            override fun areItemsTheSame(oldItem: StoryResponseItem, newItem: StoryResponseItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: StoryResponseItem, newItem: StoryResponseItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }



}