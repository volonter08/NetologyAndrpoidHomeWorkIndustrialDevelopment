package com.example.netologyandroidhomework1

data class FeedModelState (
    val loading: Boolean = false,
    val isRefreshed:Boolean = false,
    val error: Boolean = false,
    val errorRetryListener: OnRetryListener?= null
)