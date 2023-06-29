package com.example.netologyandroidhomework1.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class PostRepository : Repository<List<Post>> {
    var nextId:Int = 1
    var listPost: List<Post> = listOf(
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий будущего",
            published = "21 мая в 21:56",
            countLiked = 889,
            countShared = 888,
        ),
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий будущего",
            published = "21 мая в 21:56",
            countLiked = 900,
            countShared = 888,
        ),
    )
    val data: MutableLiveData<List<Post>> = MutableLiveData(listPost)
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
        return returnedBooleanValue
    }

    fun share(id: Int) {
        listPost = listPost.map {
            if (it.id == id) {
                it.copy(countShared = it.countShared + 1)
            } else it
        }
        data.value = listPost
    }
    fun remove(id:Int){
        listPost = listPost.filter {
            it.id!=id
        }
        data.value = listPost
    }
    fun createPost(content:String){
        val newPost = Post(
            id = nextId++,
            content = content,
            published = "now"
        )
        listPost = listOf(newPost) + listPost
        data.value = listPost
    }
    fun update(post:Post){
        listPost = listPost.map {
            if (it.id == post.id) {
               post
            } else it
        }
        data.value = listPost
    }
}