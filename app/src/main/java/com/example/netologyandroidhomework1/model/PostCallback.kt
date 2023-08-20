package com.example.netologyandroidhomework1.model

interface PostCallback {
    fun onLoadPosts(listPosts:List<Post>,isSuccessFul:Boolean)
    fun onLike(likedPost: Post?,isSuccessFul:Boolean)
    fun onDisLike(disLikedPost: Post?,isSuccessFul:Boolean)
    fun onUpdate(updatedPost:Post?,isSuccessFul:Boolean)
    fun onCreatePost(createdPost:Post?,isSuccessFul:Boolean)
    fun onRemove(id:Long,isSuccessFul:Boolean)
    fun onError()

}