package com.example.submissionapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submissionapp.data.remote.response.StoryResponseItem
import com.example.submissionapp.databinding.ItemUserStoryBinding

class ListStoryAdapter(private val listStory : List<StoryResponseItem>): RecyclerView.Adapter<ListStoryAdapter.ListViewHolder>(){

    private lateinit var onItemClickCallback : OnItemClickCallback

    interface OnItemClickCallback{
        fun onItemClicked(data: StoryResponseItem)
    }

    fun setItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder(var binding: ItemUserStoryBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) :ListViewHolder {
         val binding = ItemUserStoryBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
         return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (photo, title ,name) = listStory[position]
        Glide.with(holder.itemView.context).load(photo).into(holder.binding.photoStory)
        holder.binding.userName.text = name
        holder.binding.storyTitle.text = title
        holder.itemView.setOnClickListener{onItemClickCallback.onItemClicked(listStory[holder.adapterPosition])}

    }

    override fun getItemCount(): Int {
        return listStory.size
    }
}