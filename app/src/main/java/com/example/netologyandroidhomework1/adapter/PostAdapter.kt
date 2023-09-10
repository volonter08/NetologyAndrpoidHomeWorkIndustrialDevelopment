package com.example.netologyandroidhomework1.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.netologyandroidhomework1.OnButtonTouchListener
import com.example.netologyandroidhomework1.databinding.PostBinding
import com.example.netologyandroidhomework1.diffutill.PostDiffCallback
import com.example.netologyandroidhomework1.dto.Post
import com.example.netologyandroidhomework1.viewHolder.PostHolder

class PostAdapter(val context:Context,
    private val  listener:OnButtonTouchListener
) : ListAdapter<Post, PostHolder>(PostDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val binding = PostBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PostHolder(context,binding, listener)
    }
    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        val post = currentList[position]
        holder.bind(post)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }
}