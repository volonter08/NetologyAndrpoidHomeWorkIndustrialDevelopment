package com.example.netologyandroidhomework1.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.netologyandroidhomework1.model.Post
import com.example.netologyandroidhomework1.model.PostRepository

class PostViewModel(application: Application): AndroidViewModel(application) {
    private val repository = PostRepository(application)
    val data = repository.get()

    fun likeOrDislike(id:Int){
        if(!repository.like(id))
            repository.dislike(id)
    }
    fun share(id:Int){
        repository.share(id)
    }
    fun remove(id:Int){
        repository.remove(id)
    }
    fun createPost(content:String){
        repository.createPost(content)
    }
    fun update(post: Post){
        repository.update(post)
    }
}