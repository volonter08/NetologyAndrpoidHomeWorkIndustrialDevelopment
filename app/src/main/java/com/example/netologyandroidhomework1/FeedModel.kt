package com.example.netologyandroidhomework1

import com.example.netologyandroidhomework1.model.Post
import com.example.netologyandroidhomework1.model.PostWithAuthor

data class FeedModel(
    val posts: List<Post> = emptyList(),
    val loading: Boolean = false,
    val isSuccessFull:Boolean = true,
    val error: Boolean = false,
    val empty: Boolean = false
) {

}