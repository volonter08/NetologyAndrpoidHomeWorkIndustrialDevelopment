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
        override fun onLoadPosts(listPosts: List<Post>) {
            _data.postValue(
                FeedModel(listPosts, empty = listPosts.isEmpty())
            )
        }

        override fun onLike(likedPost: Post) {
            _data.postValue(FeedModel(posts = _data.value!!.posts.map {
                if (it.id == likedPost.id) {
                    likedPost
                } else
                    it
            }))
        }

        override fun onDisLike(disLikedPost: Post) {
            _data.postValue(FeedModel(posts = _data.value!!.posts.map {
                if (it.id == disLikedPost.id) {
                    disLikedPost
                } else
                    it
            }))
        }

        override fun onUpdate(updatedPost: Post) {
            _data.postValue(FeedModel(data.value!!.posts.map {
                if (it.id == updatedPost.id) {
                    updatedPost
                } else
                    it
            }))
        }

        override fun onCreatePost(createdPost: Post) {
            _data.postValue(
                FeedModel(_data.value!!.posts + listOf(createdPost))
            )
        }

        override fun onRemove(id: Long) {
            _data.postValue(
                _data.value?.copy(posts = _data.value?.posts.orEmpty()
                    .filter { it.id != id }
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