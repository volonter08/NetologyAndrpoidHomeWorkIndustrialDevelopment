package com.example.netologyandroidhomework1.model

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody


class PostRepository(context: Context) : Repository<List<Post>> {
    companion object {
        const val BASE_URL = "http://10.0.2.2:9999/"
    }
    var nextId: Long = 1
    private val typeToken = object : TypeToken<List<Post>>() {}
    val client = OkHttpClient()
    val gson = Gson()
    private val jsonType = "application/json".toMediaType()
    fun sync() {

    }
    override fun getAll(): List<Post> {
        val postsRequest = Request.Builder().url("${BASE_URL}api/posts").build()
        val response = client.newCall(postsRequest).execute()
        val string = response.body?.string()
        return gson.fromJson(string, typeToken)

    }
    fun like(id: Long): Post {
        val reqbody = RequestBody.create(null, ByteArray(0))
        val postsRequestLike = Request.Builder().url("${BASE_URL}api/posts/$id/likes").method("POST",reqbody).header("Content-Length", "0").build()
        val updatedPost= client.newCall(postsRequestLike).execute().use {
            gson.fromJson(it.body?.string(),TypeToken.get(Post::class.java))
        }
        return updatedPost
        /*var returnedBooleanValue = false
        listPost = listPost.map {
            if (it.id == id) {
                if (!it.isLiked) {
                    returnedBooleanValue = true
                    it.copy(countLiked = it.countLiked + 1, isLiked = true)
                } else
                    it
            } else it
        }
        data.value = listPost
        sync()
        return returnedBooleanValue

         */
    }

    fun dislike(id: Long): Post {
        val postsRequestDislike = Request.Builder().url("${BASE_URL}api/posts/$id/likes").delete().build()
        val updatedPost= client.newCall(postsRequestDislike).execute().use {
            gson.fromJson(it.body?.string(),TypeToken.get(Post::class.java))
        }
        return updatedPost

        /*var returnedBooleanValue = false
        listPost = listPost.map {
            if (it.id == id) {
                if (it.isLiked) {
                    returnedBooleanValue = true
                    it.copy(countLiked = it.countLiked - 1, isLiked = false)
                } else
                    it
            } else it
        }
        data.value = listPost
        sync()
        return returnedBooleanValue

         */
    }
    fun share(id: Int) {
    }

    fun remove(id: Long) {
        val postsRequestRemove = Request.Builder().url("${BASE_URL}api/posts/$id").delete().build()
        client.newCall(postsRequestRemove).execute()
    }
    fun createPost(content: String):Post{
        val postsRequestCreate = Request.Builder().url("${BASE_URL}api/posts").post(
            gson.toJson(Post(content = content))
                .toRequestBody(jsonType)
        ).build()
        val createdPost = client.newCall(postsRequestCreate).execute().use{
             gson.fromJson(it.body?.string(),Post::class.java)
        }
        return createdPost
    }
    fun update(post: Post):Post {
        val postsRequestUpdate = Request.Builder().url("${BASE_URL}api/posts").post(
            gson.toJson(post)
                .toRequestBody(jsonType)
        ).build()
        val updatedPost = client.newCall(postsRequestUpdate).execute().use{
            gson.fromJson(it.body?.string(),Post::class.java)
        }
        return updatedPost
    }
}