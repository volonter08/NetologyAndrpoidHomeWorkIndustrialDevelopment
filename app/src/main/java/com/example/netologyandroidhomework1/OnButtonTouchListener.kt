package com.example.netologyandroidhomework1

import com.example.netologyandroidhomework1.model.Post

interface OnButtonTouchListener {
    fun onLikeCLick(id:Long)
    fun onDislikeCLick(id:Long)
    fun onShareCLick(post: Post)
    fun onRemoveClick(id:Long)
    fun onUpdateCLick(post: Post)
    fun onCreateClick()
}