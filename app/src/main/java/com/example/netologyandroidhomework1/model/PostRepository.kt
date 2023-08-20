package com.example.netologyandroidhomework1.model

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.BaseBundle
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.netologyandroidhomework1.OkHttpClientObject
import com.example.netologyandroidhomework1.RetrofitObject
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Retrofit
import java.io.File
import java.io.IOException
import java.net.URI
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import kotlin.contracts.contract


class PostRepository(val context: Context, val postCallback: PostCallback) :
    Repository<List<Post>> {
    companion object {
        const val BASE_URL = "http://10.0.2.2:9999/"
    }

    private val typeToken = object : TypeToken<List<Post>>() {}
    val client = OkHttpClientObject.get()
    val gson = Gson()
    val retrofitService = RetrofitObject.service
    private val jsonType = "application/json".toMediaType()
    override fun getAll() {
        val postsRequest = Request.Builder().url("${BASE_URL}api/posts").build()
        retrofitService.getAll().enqueue(object : Callback<List<Post>> {

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                t.printStackTrace()
                postCallback.onError()
            }

            override fun onResponse(
                call: Call<List<Post>>,
                response: Response<List<Post>>
            ) {
                val listPosts = response.body()
                postCallback.onLoadPosts(listPosts ?: emptyList(), response.isSuccessful)
            }
        })
    }

    fun like(id: Long) {
        val reqbody = RequestBody.create(null, ByteArray(0))
        val postsRequestLike =
            Request.Builder().url("${BASE_URL}api/posts/$id/likes").method("POST", reqbody)
                .header("Content-Length", "0").build()
        retrofitService.likeById(id).enqueue(object : Callback<Post> {
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
        val postsRequestDislike =
            Request.Builder().url("${BASE_URL}api/posts/$id/likes").delete().build()
        retrofitService.dislikeById(id).enqueue(object : Callback<Post> {
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
        val postsRequestRemove = Request.Builder().url("${BASE_URL}api/posts/$id").delete().build()
        retrofitService.removeById(id).enqueue(object : Callback<Unit> {
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                postCallback.onError()
            }

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                postCallback.onRemove(id, response.isSuccessful)
            }
        })
    }

    fun createPost(content: String) {
        val postsRequestCreate = Request.Builder().url("${BASE_URL}api/posts").post(
            gson.toJson(Post(content = content))
                .toRequestBody(jsonType)
        ).build()
        retrofitService.save(Post(content = content)).enqueue(object : Callback<Post> {
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
        val postsRequestUpdate = Request.Builder().url("${BASE_URL}api/posts").post(
            gson.toJson(post)
                .toRequestBody(jsonType)
        ).build()
        retrofitService.save(post).enqueue(object : Callback<Post> {
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