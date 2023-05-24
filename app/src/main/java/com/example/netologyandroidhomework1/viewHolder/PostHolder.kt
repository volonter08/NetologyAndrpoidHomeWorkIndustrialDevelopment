package com.example.netologyandroidhomework1.viewHolder

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding
import com.example.netologyandroidhomework1.R
import com.example.netologyandroidhomework1.databinding.PostBinding
import com.example.netologyandroidhomework1.model.Post
import com.example.netologyandroidhomework1.utills.ConverterCountFromIntToString

class PostHolder(private val binding: PostBinding,val onLikeClick: (Int) -> Unit,val onShareClick:(Int)->Unit) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post){
        val likeButton = binding.like
        val shareButton = binding.share
        likeButton.setOnClickListener{
            onLikeClick(post.id)
        }
        shareButton.setOnClickListener{
            onShareClick(post.id)
        }
        binding.apply {
            author.text= post.author
            date.text = post.published
            content.text= post.content
            countLiked.text = ConverterCountFromIntToString.convertCount(post.countLiked)
            countShared.text = ConverterCountFromIntToString.convertCount(post.countShared)
            like.setImageResource(if(post.isLiked) (R.drawable.baseline_favorite_24) else
                (R.drawable.baseline_favorite_border_24))
        }
    }
}