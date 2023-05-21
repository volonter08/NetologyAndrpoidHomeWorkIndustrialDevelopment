package com.example.netologyandroidhomework1.viewModel

import androidx.lifecycle.ViewModel
import com.example.netologyandroidhomework1.model.ConverterCountFromIntToString
import com.example.netologyandroidhomework1.model.PostRepository

class PostViewModel: ViewModel() {
    private val repository = PostRepository()
    val data = repository.get()

    fun likeOrDislike(){
        if(!repository.like())
            repository.dislike()
        repository.notifyData()
    }
    fun share(){
        repository.share()
        repository.notifyData()
    }
}