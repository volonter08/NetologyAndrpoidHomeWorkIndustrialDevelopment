package com.example.netologyandroidhomework1.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.netologyandroidhomework1.FeedModel
import com.example.netologyandroidhomework1.model.Post
import com.example.netologyandroidhomework1.model.PostCallback
import com.example.netologyandroidhomework1.model.PostRepository
import com.example.netologyandroidhomework1.utills.SingleLiveEvent
import com.google.gson.JsonSyntaxException
import java.io.IOException
import kotlin.concurrent.thread

class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val postCallback = object : PostCallback {
        override fun onLoadPosts(listPosts: List<Post>,isSuccessFul:Boolean) {
            _data.postValue(
                FeedModel(listPosts, empty = listPosts.isEmpty(), isSuccessFull = isSuccessFul)
            )
        }

        override fun onLike(likedPost: Post?,isSuccessFul:Boolean) {
            _data.postValue(FeedModel(posts = if(isSuccessFul) _data.value!!.posts.map {
                if (it.id == likedPost!!.id) {
                    likedPost
                } else
                    it
            } else _data.value!!.posts ,isSuccessFull = isSuccessFul))
        }

        override fun onDisLike(disLikedPost: Post?,isSuccessFul:Boolean) {
            _data.postValue(FeedModel(posts = if(isSuccessFul) _data.value!!.posts.map {
                if (it.id == disLikedPost!!.id) {
                    disLikedPost
                } else
                    it
            } else _data.value!!.posts,isSuccessFull = isSuccessFul))
        }

        override fun onUpdate(updatedPost: Post?,isSuccessFul:Boolean) {
            _data.postValue(FeedModel(if(isSuccessFul) data.value!!.posts.map {
                if (it.id == updatedPost!!.id) {
                    updatedPost!!
                } else
                    it
            } else data.value!!.posts,isSuccessFull = isSuccessFul))
        }

        override fun onCreatePost(createdPost: Post?,isSuccessFul:Boolean) {
            _data.postValue(
                FeedModel(if(isSuccessFul) listOf(createdPost!!)+ _data.value!!.posts else _data.value!!.posts ,isSuccessFull = isSuccessFul )
            )
        }

        override fun onRemove(id: Long,isSuccessFul:Boolean) {
            _data.postValue(
                _data.value?.copy(posts = if(isSuccessFul) _data.value!!.posts
                    .filter { it.id != id }else _data.value!!.posts ,isSuccessFull = isSuccessFul
                )
            )
        }

        override fun onError() {
            _data.postValue(
                FeedModel(error = true)
            )
        }
    }

    private val repository = PostRepository( application,postCallback)
    private val _data: MutableLiveData<FeedModel> = MutableLiveData(FeedModel())
    val data: LiveData<FeedModel>
        get() = _data

    init {
        loadPosts()
    }

    fun loadPosts() {
        _data.postValue(FeedModel(loading = true))
        repository.getAll()
    }

    fun like(id: Long) {
        repository.like(id)
    }

    fun dislike(id: Long) {
        repository.dislike(id)
    }

    fun share(id: Int) {
        repository.share(id)
    }

    fun remove(id: Long) {
        repository.remove(id)
    }

    fun createPost(content: String) {
        repository.createPost(content)
    }

    fun update(post: Post) {
        repository.update(post)
    }
}