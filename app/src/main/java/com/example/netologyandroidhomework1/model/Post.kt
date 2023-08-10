package com.example.netologyandroidhomework1.model

data class Post(
    val id: Long = 0,
    val author: String = "Нетология. Университет интернет-профессий будущего",
    val authorAvatar:String? = null,
    val content: String,
    val published: String = "",
    val likedByMe: Boolean = false,
    val likes: Int = 0,
    val attachment:Attachment? = null
):java.io.Serializable