package com.example.netologyandroidhomework1.model

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
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
    val client = OkHttpClient()
    val gson = Gson()
    private val jsonType = "application/json".toMediaType()
    override fun getAll() {
        val postsRequest = Request.Builder().url("${BASE_URL}api/posts").build()
        client.newCall(postsRequest).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                postCallback.onError()
            }

            override fun onResponse(call: Call, response: Response) {
                val listPosts = gson.fromJson(response.body?.string(), typeToken)
                val loadedAvatarsPosts = AtomicInteger(0)
                val isAvatarsLoaded = AtomicBoolean(false)
                listPosts.filter { it.authorAvatar != null }.apply {
                    forEach {
                        client.newCall(
                            Request.Builder().url("${BASE_URL}avatars/${it.authorAvatar}").build()
                        )
                            .enqueue(object : Callback {
                                override fun onFailure(call: Call, e: IOException) {

                                }

                                override fun onResponse(call: Call, response: Response) {
                                    context.openFileOutput(it.authorAvatar, MODE_PRIVATE).use {
                                        it.write(response.body!!.bytes())
                                    }
                                    loadedAvatarsPosts.incrementAndGet()
                                    if (loadedAvatarsPosts.get() == size && isAvatarsLoaded.compareAndSet(
                                            false,
                                            true
                                        )
                                    )
                                        postCallback.onLoadPosts(listPosts)
                                }
                            })
                    }
                }
            }
        })
    }

    fun like(id: Long) {
        val reqbody = RequestBody.create(null, ByteArray(0))
        val postsRequestLike =
            Request.Builder().url("${BASE_URL}api/posts/$id/likes").method("POST", reqbody)
                .header("Content-Length", "0").build()
        client.newCall(postsRequestLike).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                postCallback.onError()
            }

            override fun onResponse(call: Call, response: Response) {
                val likedPost =
                    gson.fromJson(response.body?.string(), TypeToken.get(Post::class.java))

                postCallback.onLike(likedPost)
            }
        })
    }

    fun dislike(id: Long) {
        val postsRequestDislike =
            Request.Builder().url("${BASE_URL}api/posts/$id/likes").delete().build()
        client.newCall(postsRequestDislike).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                postCallback.onError()
            }

            override fun onResponse(call: Call, response: Response) {
                val disLikedPost =
                    gson.fromJson(response.body?.string(), TypeToken.get(Post::class.java))
                postCallback.onDisLike(disLikedPost)
            }
        })
    }

    fun share(id: Int) {
    }

    fun remove(id: Long) {
        val postsRequestRemove = Request.Builder().url("${BASE_URL}api/posts/$id").delete().build()
        client.newCall(postsRequestRemove).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                postCallback.onError()
            }

            override fun onResponse(call: Call, response: Response) {
                postCallback.onRemove(id)
            }
        })
    }

    fun createPost(content: String) {
        val postsRequestCreate = Request.Builder().url("${BASE_URL}api/posts").post(
            gson.toJson(Post(content = content))
                .toRequestBody(jsonType)
        ).build()
        client.newCall(postsRequestCreate).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                postCallback.onError()
            }

            override fun onResponse(call: Call, response: Response) {
                val createdPost = gson.fromJson(response.body?.string(), Post::class.java)
                postCallback.onCreatePost(createdPost)
            }
        })
    }

    fun update(post: Post) {
        val postsRequestUpdate = Request.Builder().url("${BASE_URL}api/posts").post(
            gson.toJson(post)
                .toRequestBody(jsonType)
        ).build()
        client.newCall(postsRequestUpdate).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                postCallback.onError()
            }

            override fun onResponse(call: Call, response: Response) {
                val updatedPost = gson.fromJson(response.body?.string(), Post::class.java)
                postCallback.onUpdate(updatedPost)
            }
        })
    }
}