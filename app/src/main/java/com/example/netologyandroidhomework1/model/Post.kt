package com.example.netologyandroidhomework1.model

data class Post(
    var countLiked: Int = 0,
    var countShared: Int = 0,
) {
    var isLiked: Boolean = false
}