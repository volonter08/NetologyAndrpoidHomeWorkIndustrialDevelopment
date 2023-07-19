package com.example.netologyandroidhomework1.viewHolder

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.view.View
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.netologyandroidhomework1.OnButtonTouchListener
import com.example.netologyandroidhomework1.R
import com.example.netologyandroidhomework1.databinding.PostBinding
import com.example.netologyandroidhomework1.model.Post
import com.example.netologyandroidhomework1.utills.ConverterCountFromIntToString
import java.net.URI

class PostHolder(
    private val binding: PostBinding,
    val listener: OnButtonTouchListener
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
                        R.id.update -> {
                            listener.onUpdateCLick(post)
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
            if (!post.likedByMe)
                listener.onLikeCLick(post.id)
            else
                listener.onDislikeCLick(post.id)
        }
        shareButton.setOnClickListener {
            listener.onShareCLick(post)
        }
        binding.apply {
            author.text = post.author
            date.text = post.published
            content.text = post.content
            like.text = ConverterCountFromIntToString.convertCount(post.likes)
            like.isChecked = post.likedByMe
        }
    }
}