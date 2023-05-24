package com.example.netologyandroidhomework1

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.netologyandroidhomework1.adapter.PostAdapter
import com.example.netologyandroidhomework1.databinding.ActivityMainBinding
import com.example.netologyandroidhomework1.utills.ConverterCountFromIntToString
import com.example.netologyandroidhomework1.viewModel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val viewBinding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        val viewModel:PostViewModel by viewModels()
        val postAdapter= PostAdapter(viewModel::likeOrDislike,viewModel::share)
        viewBinding.root.adapter = postAdapter
        viewModel.data.observe(this){ posts->
            postAdapter.submitList(posts)
        }
    }
}