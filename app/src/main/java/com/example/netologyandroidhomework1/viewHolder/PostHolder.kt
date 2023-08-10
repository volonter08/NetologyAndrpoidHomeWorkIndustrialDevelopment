package com.example.netologyandroidhomework1.viewHolder

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Point
import android.graphics.drawable.AnimatedImageDrawable
import android.net.Uri
import android.os.Build
import android.util.TypedValue
import android.view.Display
import android.view.WindowManager
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.netologyandroidhomework1.OnButtonTouchListener
import com.example.netologyandroidhomework1.R
import com.example.netologyandroidhomework1.databinding.PostBinding
import com.example.netologyandroidhomework1.model.Post
import com.example.netologyandroidhomework1.model.PostRepository.Companion.BASE_URL
import com.example.netologyandroidhomework1.utills.ConverterCountFromIntToString


class PostHolder(
    val context: Context,
    private val binding: PostBinding,
    val listener: OnButtonTouchListener
) : RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("UseCompatLoadingForDrawables")
    fun bind(post: Post) {
        val likeButton = binding.like
        val shareButton = binding.share
        val valueInDp = 64
        val valueInPx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, valueInDp.toFloat(), context.resources.displayMetrics
        ).toInt()
        binding.avatar.also {
            if(post.authorAvatar!=null){
                val animPlaceholder =
                    context.getDrawable(R.drawable.loading_avatar) as AnimatedImageDrawable
                animPlaceholder.start() // probably needed
                Glide.with(context).load(Uri.parse("${BASE_URL}avatars/${post.authorAvatar}")).placeholder(animPlaceholder).circleCrop().into(it)
            }
        }
        binding.attachment.also {
            if(post.attachment!=null){
                Glide.with(context).load(Uri.parse("${BASE_URL}images/${post.attachment.url}")).into(it)
            }
        }
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