package com.example.netologyandroidhomework1

import okhttp3.OkHttpClient

object OkHttpClientObject {
    private val client = OkHttpClient.Builder().build()
        fun get(): OkHttpClient {
            return client
        }
}