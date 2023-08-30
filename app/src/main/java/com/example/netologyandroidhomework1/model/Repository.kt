package com.example.netologyandroidhomework1.model

import androidx.lifecycle.LiveData

interface  Repository<T> {
    suspend fun getAll():List<Post>
}