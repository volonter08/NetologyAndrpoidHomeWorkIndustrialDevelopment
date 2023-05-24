package com.example.netologyandroidhomework1.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.netologyandroidhomework1.databinding.PostBinding
import com.example.netologyandroidhomework1.diffutill.PostDiffCallback
import com.example.netologyandroidhomework1.model.Post
import com.example.netologyandroidhomework1.viewHolder.PostHolder

class PostAdapter(val onLikeClick:(Int)->Unit,val onShareClick:(Int)->Unit) :ListAdapter<Post,PostHolder>(PostDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val binding = PostBinding.inflate(LayoutInflater.from(parent.context))
        return PostHolder(binding,onLikeClick,onShareClick)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}