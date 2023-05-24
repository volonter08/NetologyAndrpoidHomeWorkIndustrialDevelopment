package com.example.netologyandroidhomework1.diffutill

import androidx.recyclerview.widget.DiffUtil
import com.example.netologyandroidhomework1.model.Post

class PostDiffCallback: DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean=
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean=
        oldItem==newItem
}