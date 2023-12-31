package com.example.netologyandroidhomework1.dto

import com.example.netologyandroidhomework1.model.Attachment

data class Post(
    val id: Long = 0,
    val author: String = "",
    val authorAvatar: String?= null,
    val content: String,
    val published: String = "",
    val likedByMe: Boolean = false,
    val likes: Int = 0,
    val attachment: Attachment? = null
):java.io.Serializable