package com.example.netologyandroidhomework1

import com.example.netologyandroidhomework1.model.Post

interface OnButtonTouchListener {
    fun onLikeCLick(id:Int)
    fun onShareCLick(post: Post)
    fun onRemoveClick(id:Int)
    fun onUpdateCLick(post: Post)
    fun onCreateClick()
    fun onStartVideo(post:Post)
}