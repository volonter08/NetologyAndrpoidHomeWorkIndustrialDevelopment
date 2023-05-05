package com.example.netologyandroidhomework1.viewModel

import androidx.lifecycle.ViewModel
import com.example.netologyandroidhomework1.model.PostRepository

class PostViewModel: ViewModel() {
    private val repository = PostRepository()
    val data = repository.get()

    fun likeOrDislike(){
        if(!repository.post.like())
            repository.post.dislike()
        repository.notifyData()
    }
    fun share(){
        repository.post.share()
        repository.notifyData()
    }
    fun getCountLiked():String{
        return repository.post.textCountLiked
    }
    fun getCountShared():String{
        return repository.post.textCountShared
    }
}