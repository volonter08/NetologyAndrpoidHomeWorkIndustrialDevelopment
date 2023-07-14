package com.example.netologyandroidhomework1.model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PostRepository(context: Context) : Repository<List<Post>> {
    var nextId:Int = 1
    var listPost: List<Post> = emptyList()
    val data: MutableLiveData<List<Post>> = MutableLiveData(listPost)
    val fileName = "posts.json"
    val type = TypeToken.getParameterized(List::class.java,Post::class.java).type
    val file = context.filesDir.resolve(fileName)
    init {
        if(file.exists()){
            file.bufferedReader().use{
                try {
                    listPost = Gson().fromJson(it,type)
                    data.value = listPost
                }
                catch(_:Exception) {
                }
            }
        }
        else{
            file.createNewFile()
        }
    }


    fun sync(){
        file.bufferedWriter().use {
            it.write(Gson().toJson(listPost))
        }
    }

    override fun get(): LiveData<List<Post>> {
        return data
    }

    fun like(id: Int): Boolean {
        var returnedBooleanValue = false
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
    }

    fun dislike(id: Int): Boolean {
        var returnedBooleanValue = false
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
    }

    fun share(id: Int) {
        listPost = listPost.map {
            if (it.id == id) {
                it.copy(countShared = it.countShared + 1)
            } else it
        }
        data.value = listPost
        sync()
    }
    fun remove(id:Int){
        listPost = listPost.filter {
            it.id!=id
        }
        data.value = listPost
        sync()
    }
    fun createPost(content:String){
        val newPost = Post(
            id = nextId++,
            content = content,
            published = "now"
        )
        listPost = listOf(newPost) + listPost
        data.value = listPost
        sync()
    }
    fun update(post:Post){
        listPost = listPost.map {
            if (it.id == post.id) {
               post
            } else it
        }
        data.value = listPost
        sync()
    }
}