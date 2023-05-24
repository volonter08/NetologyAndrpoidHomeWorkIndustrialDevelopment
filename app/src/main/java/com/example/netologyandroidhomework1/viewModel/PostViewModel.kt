package com.example.netologyandroidhomework1.viewModel

import androidx.lifecycle.ViewModel
import com.example.netologyandroidhomework1.model.PostRepository

class PostViewModel: ViewModel() {
    private val repository = PostRepository()
    val data = repository.get()

    fun likeOrDislike(id:Int){
        if(!repository.like(id))
            repository.dislike(id)
    }
    fun share(id:Int){
        repository.share(id)
    }
}