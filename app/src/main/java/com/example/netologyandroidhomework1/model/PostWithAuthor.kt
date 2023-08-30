package com.example.netologyandroidhomework1.model

data class PostWithAuthor (
    val id: Long = 0,
    val author: Author,
    val content: String,
    val published: String = "",
    val likedByMe: Boolean = false,
    val likes: Int = 0,
    val attachment: Attachment? = null
)