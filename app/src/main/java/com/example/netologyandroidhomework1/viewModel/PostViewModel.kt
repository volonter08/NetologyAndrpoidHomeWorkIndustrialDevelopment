package com.example.netologyandroidhomework1.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.netologyandroidhomework1.FeedModel
import com.example.netologyandroidhomework1.model.Post
import com.example.netologyandroidhomework1.model.PostRepository
import com.example.netologyandroidhomework1.utills.SingleLiveEvent
import com.google.gson.JsonSyntaxException
import java.io.IOException
import kotlin.concurrent.thread

class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = PostRepository(application)
    private val _data: MutableLiveData<FeedModel> = MutableLiveData(FeedModel())
    val data: LiveData<FeedModel>
        get() = _data
    private val _postCreated = SingleLiveEvent<Unit>()
    val postCreated: LiveData<Unit>
        get() = _postCreated
    var postCreatedFlag = 0
    init {
        loadPosts()
    }

    fun loadPosts() {
        thread {
            _data.postValue(FeedModel(loading = true))
            _data.postValue(
            try {
                val listPosts = repository.getAll()
                FeedModel(listPosts, empty = listPosts.isEmpty())
            } catch (e: JsonSyntaxException) {
                FeedModel(error = true)
            })
        }
    }

    fun like(id: Long) {
        thread {
            try {
                val likedPost = repository.like(id)
                FeedModel(_data.value!!.posts.map {
                    if (it.id == likedPost.id) {
                        likedPost
                    } else
                        it
                })
            } catch (e: Exception) {
                FeedModel(error = true)
            }.run(_data::postValue)
        }
    }

    fun dislike(id: Long) {
        thread {
            try {
                val disLikedPost = repository.dislike(id)
                FeedModel(_data.value!!.posts.map {
                    if (it.id == disLikedPost.id) {
                        disLikedPost
                    } else
                        it
                })
            } catch (e: Exception) {
                FeedModel(error = true)
            }.run(_data::postValue)
        }
    }

    fun share(id: Int) {
        repository.share(id)
    }

    fun remove(id: Long) {
        thread {
            val old = _data.value?.posts.orEmpty()
            _data.postValue(
                _data.value?.copy(posts = _data.value?.posts.orEmpty()
                    .filter { it.id != id }
                )
            )
            try {
                repository.remove(id)
            } catch (e: IOException) {
                _data.postValue(_data.value?.copy(posts = old))
            }
        }
    }

    fun createPost(content: String) {
        thread {
            try {
                val createdPost = repository.createPost(content)
                FeedModel(_data.value!!.posts + listOf(createdPost))
            }
            catch (e: Exception) {
                FeedModel(error = true)
            }.run(_data::postValue)
        }
    }

    fun update(post: Post){
        thread {
            try {
                val updatedPost = repository.update(post)
                FeedModel(data.value!!.posts.map {
                    if (it.id == updatedPost.id) {
                        updatedPost
                    } else
                        it
                })
            }
            catch (e: Exception) {
                FeedModel(error = true)
            }.run(_data::postValue)
        }
    }
}