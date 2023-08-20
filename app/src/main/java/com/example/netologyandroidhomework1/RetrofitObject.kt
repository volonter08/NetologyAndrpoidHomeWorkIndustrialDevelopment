package com.example.netologyandroidhomework1

import com.example.netologyandroidhomework1.model.Post
import com.example.netologyandroidhomework1.model.PostRepository.Companion.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

object RetrofitObject {
    val service by lazy{
        Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL).client(OkHttpClientObject.get()).build().create(PostsApiService::class.java)
    }
}
interface PostsApiService {
    @GET("api/post")
    fun getAll(): Call<List<Post>>

    @GET("api/posts/{id}")
    fun getById(@Path("id") id: Long): Call<Post>

    @POST("api/posts")
    fun save(@Body post: Post): Call<Post>

    @DELETE("api/posts/{id}")
    fun removeById(@Path("id") id: Long): Call<Unit>

    @POST("api/posts/{id}/likes")
    fun likeById(@Path("id") id: Long): Call<Post>

    @DELETE("api/posts/{id}/likes")
    fun dislikeById(@Path("id") id: Long): Call<Post>
}