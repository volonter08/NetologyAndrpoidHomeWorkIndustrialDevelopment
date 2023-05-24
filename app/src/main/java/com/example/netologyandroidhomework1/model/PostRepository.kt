package com.example.netologyandroidhomework1.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class PostRepository : Repository<List<Post>> {
    var listPost: List<Post> = listOf(
        Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "21 мая в 21:56",
            countLiked = 889,
            countShared = 888,
        ),
        Post(
            id = 2,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
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
}