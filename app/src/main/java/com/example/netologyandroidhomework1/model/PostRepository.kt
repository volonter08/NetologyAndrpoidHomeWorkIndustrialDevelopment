package com.example.netologyandroidhomework1.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class PostRepository:Repository {
    val post:Post = Post(
        countLiked = 889,
        countShared = 888,
    )
    val data: MutableLiveData<Post> = MutableLiveData(post)
    override fun get(): LiveData<Post> {
        return data
    }
    fun like():Boolean{
        if (!post.isLiked) {
            post.countLiked++
            post.isLiked = true
            data.value = post
            return true
        }
        return false
    }
    fun dislike():Boolean{
        if (post.isLiked) {
            post.countLiked--
            post.isLiked = false
            data.value = post
            return true
        }
        return false
    }

    fun share() {
        post.countShared++
        data.value = post
    }
}