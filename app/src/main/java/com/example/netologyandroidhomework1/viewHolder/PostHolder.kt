package com.example.netologyandroidhomework1.viewHolder

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Point
import android.graphics.drawable.AnimatedImageDrawable
import android.net.Uri
import android.opengl.Visibility
import android.os.Build
import android.util.TypedValue
import android.view.Display
import android.view.View
import android.view.WindowManager
import android.widget.PopupMenu
import androidx.core.graphics.convertTo
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.netologyandroidhomework1.OnButtonTouchListener
import com.example.netologyandroidhomework1.R
import com.example.netologyandroidhomework1.RetrofitObject
import com.example.netologyandroidhomework1.databinding.PostBinding
import com.example.netologyandroidhomework1.model.Post
import com.example.netologyandroidhomework1.model.PostRepository.Companion.BASE_URL
import com.example.netologyandroidhomework1.utills.ConverterCountFromIntToString
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import kotlin.coroutines.EmptyCoroutineContext


class PostHolder(
    val context: Context,
    private val binding: PostBinding,
    val listener: OnButtonTouchListener
) : RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("UseCompatLoadingForDrawables")
    suspend fun bind(post: Post) {
        CoroutineScope(EmptyCoroutineContext).run {
            val likeButton = binding.like
            val shareButton = binding.share
            val valueInDp = 64
            val author = async {  RetrofitObject.authorService.getAuthor(post.authorId).execute().body()}.await()!!
            withContext(Dispatchers.Main) {
                binding.avatar.also {
                    if (author.avatar != null) {
                        val animPlaceholder =
                            context.getDrawable(R.drawable.loading_avatar) as AnimatedImageDrawable
                        animPlaceholder.start() // probably needed
                        Glide.with(context)
                            .load(Uri.parse("${BASE_URL}avatars/${author.avatar}"))
                            .placeholder(animPlaceholder).timeout(10_000).circleCrop().into(it)
                    }
                }
                binding.attachment.also {
                    if (post.attachment != null) {
                        Glide.with(context)
                            .load(Uri.parse("${BASE_URL}images/${post.attachment.url}"))
                            .timeout(10_000).into(it)
                        it.visibility = View.VISIBLE
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
                    this.author.text = author.name
                    date.text = post.published
                    content.text = post.content
                    like.text = ConverterCountFromIntToString.convertCount(post.likes)
                    like.isChecked = post.likedByMe
                }
            }
        }
    }
}