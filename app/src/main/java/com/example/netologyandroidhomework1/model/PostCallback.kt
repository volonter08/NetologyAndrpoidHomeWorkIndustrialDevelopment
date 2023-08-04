package com.example.netologyandroidhomework1.model

interface PostCallback {
    fun onLoadPosts(listPosts:List<Post>)
    fun onLike(likedPost: Post)
    fun onDisLike(disLikedPost: Post)
    fun onUpdate(updatedPost:Post)
    fun onCreatePost(createdPost:Post)
    fun onRemove(id:Long)
    fun onError()

}