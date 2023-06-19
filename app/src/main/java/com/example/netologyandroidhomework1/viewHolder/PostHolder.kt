package com.example.netologyandroidhomework1.viewHolder

import android.os.Build
import android.util.Log
import android.widget.PopupMenu
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding
import com.example.netologyandroidhomework1.MainActivity
import com.example.netologyandroidhomework1.OnButtonTouchListener
import com.example.netologyandroidhomework1.R
import com.example.netologyandroidhomework1.databinding.PostBinding
import com.example.netologyandroidhomework1.model.Post
import com.example.netologyandroidhomework1.utills.ConverterCountFromIntToString

class PostHolder(
    private val binding: PostBinding,
    val listener:OnButtonTouchListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(post: Post) {
        val likeButton = binding.like
        val shareButton = binding.share
        binding.menu.setOnClickListener {
            PopupMenu(it.context, it).apply {
                inflate(R.menu.options)
                setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.remove -> {
                            listener.onRemoveClick(post.id)
                            true
                        }
                        R.id.update-> {
                          listener.onUpdateCLick(post.id,post.content)
                            true
                        }
                        else -> false
                    }
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                    setForceShowIcon(true)

            }.show()
        }
        likeButton.setOnClickListener {
            listener.onLikeCLick(post.id)
        }
        shareButton.setOnClickListener {
            listener.onShareCLick(post.id)
        }
        binding.apply {
            author.text = post.author
            date.text = post.published
            content.text = post.content
            like.text = ConverterCountFromIntToString.convertCount(post.countLiked)
            share.text = ConverterCountFromIntToString.convertCount(post.countShared)
            like.isChecked = post.isLiked
        }
    }
}