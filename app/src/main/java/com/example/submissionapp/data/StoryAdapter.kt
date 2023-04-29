package com.example.submissionapp.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submissionapp.data.remote.response.StoryResponseItem
import com.example.submissionapp.databinding.ItemUserStoryBinding

class StoryAdapter(private val listStory: ArrayList<Story>) :
    RecyclerView.Adapter<StoryAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback{
        fun onItemClicked(data:StoryResponseItem)
    }

    inner class ListViewHolder(var binding : ItemUserStoryBinding) : RecyclerView.ViewHolder(binding.root)

    fun setItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): StoryAdapter.ListViewHolder {
        val binding = ItemUserStoryBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryAdapter.ListViewHolder, position: Int) {
        val (photo, title, name) = listStory[position]
        Glide.with(holder.itemView.context).load(photo).into(holder.binding.photoStory)
        holder.binding.storyTitle.text = title
        holder.binding.userName.text = name
    }

    override fun getItemCount(): Int {
        return listStory.size
    }
}