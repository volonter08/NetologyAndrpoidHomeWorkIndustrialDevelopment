package com.example.netologyandroidhomework1.model

import androidx.lifecycle.LiveData

interface  Repository {
    fun get():LiveData<Post>
}