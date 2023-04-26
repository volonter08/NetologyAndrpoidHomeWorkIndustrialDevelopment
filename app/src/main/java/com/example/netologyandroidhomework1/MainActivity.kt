package com.example.netologyandroidhomework1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.netologyandroidhomework1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        val viewBinding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        val post = Post(
            countLiked = 1200000,
            countShared = 888,
        )
        val likeButton = viewBinding.like
        val shareButton = viewBinding.share
        val countLiked = viewBinding.countLiked.apply {
            text = post.textCountLiked
        }
        val countShared = viewBinding.countShared.apply {
            text = post.textCountShared
        }
        likeButton.setOnClickListener{
            if(!post.isLiked){
                post.like()
                likeButton.setImageResource(R.drawable.baseline_favorite_24)
            }
            else{
                post.dislike()
                likeButton.setImageResource(R.drawable.baseline_favorite_border_24)
            }
            countLiked.text = post.textCountLiked
        }
        shareButton.setOnClickListener{
            post.share()
            countShared.text = post.textCountShared
        }
    }
}