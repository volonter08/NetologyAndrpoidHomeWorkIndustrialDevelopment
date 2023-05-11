package com.example.netologyandroidhomework1

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.example.netologyandroidhomework1.databinding.ActivityMainBinding
import com.example.netologyandroidhomework1.model.Post
import com.example.netologyandroidhomework1.viewModel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val viewBinding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        val viewModel:PostViewModel by viewModels()
        viewBinding.countLiked.text = viewModel.getCountLiked()
        viewBinding.countShared.text = viewModel.getCountShared()
        viewModel.data.observe(this){ post->
            viewBinding.apply {
                countLiked.text = post.textCountLiked
                countShared.text = post.textCountShared
                like.setImageResource(if(post.isLiked) (R.drawable.baseline_favorite_24) else
                    (R.drawable.baseline_favorite_border_24))

            }
        }
        val likeButton = viewBinding.like
        val shareButton = viewBinding.share
        likeButton.setOnClickListener{
            viewModel.likeOrDislike()
        }
        shareButton.setOnClickListener{
            viewModel.share()
        }
    }
}