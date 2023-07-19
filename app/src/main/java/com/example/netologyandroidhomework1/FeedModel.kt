package com.example.netologyandroidhomework1

import com.example.netologyandroidhomework1.model.Post

data class FeedModel(
    val posts: List<Post> = emptyList(),
    val loading: Boolean = false,
    val error: Boolean = false,
    val empty: Boolean = false
) {

}