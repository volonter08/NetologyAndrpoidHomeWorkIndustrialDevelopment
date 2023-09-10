package com.example.netologyandroidhomework1.model

import com.example.netologyandroidhomework1.OnRetryListener

interface PostCallback {
    fun onError(onRetryListener: OnRetryListener)

}