package com.example.netologyandroidhomework1.model

import android.content.Context
import com.example.netologyandroidhomework1.OkHttpClientObject
import com.example.netologyandroidhomework1.RetrofitObject
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import okhttp3.MediaType.Companion.toMediaType
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class PostRepository(val context: Context, val postCallback: PostCallback) :
    Repository<List<Post>> {
    companion object {
        const val BASE_URL = "http://10.0.2.2:9999/"
    }
    val postService = RetrofitObject.postService
    override suspend fun getAll() :List<Post> {
        return suspendCoroutine{
            postService.getAll().enqueue(object : Callback<List<Post>> {
                override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                    t.printStackTrace()
                    it.resumeWithException(t)
                }

                override fun onResponse(
                    call: Call<List<Post>>,
                    response: Response<List<Post>>
                ) {
                    val listPosts = response.body()
                    it.resume(listPosts?: emptyList())
                }
            })
        }
    }

    fun like(id: Long) {
        postService.likeById(id).enqueue(object : Callback<Post> {
            override fun onFailure(call: Call<Post>, t: Throwable) {
                postCallback.onError()
            }

            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                val likedPost = response.body()
                postCallback.onLike(likedPost, response.isSuccessful)
            }
        })
    }

    fun dislike(id: Long) {
        postService.dislikeById(id).enqueue(object : Callback<Post> {
            override fun onFailure(call: Call<Post>, t: Throwable) {
                postCallback.onError()
            }

            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                val disLikedPost = response.body()
                postCallback.onDisLike(disLikedPost, response.isSuccessful)
            }
        })
    }

    fun share(id: Int) {
    }

    fun remove(id: Long) {
        postService.removeById(id).enqueue(object : Callback<Unit> {
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                postCallback.onError()
            }

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                postCallback.onRemove(id, response.isSuccessful)
            }
        })
    }

    fun createPost(content: String) {
        postService.save(Post(content = content)).enqueue(object : Callback<Post> {
            override fun onFailure(call: Call<Post>, t: Throwable) {
                postCallback.onError()
            }

            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                val createdPost = response.body()
                postCallback.onCreatePost(createdPost, response.isSuccessful)

            }
        })
    }

    fun update(post: Post) {
        postService.save(post).enqueue(object : Callback<Post> {
            override fun onFailure(call: Call<Post>, t: Throwable) {
                postCallback.onError()
            }

            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                val updatedPost = response.body()
                postCallback.onUpdate(updatedPost, response.isSuccessful)

            }
        })
    }
}