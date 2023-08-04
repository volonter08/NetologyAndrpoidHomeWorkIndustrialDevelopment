package com.example.netologyandroidhomework1.viewHolder

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import android.util.TypedValue
import android.widget.PopupMenu
import androidx.core.graphics.scale
import androidx.recyclerview.widget.RecyclerView
import com.example.netologyandroidhomework1.OnButtonTouchListener
import com.example.netologyandroidhomework1.R
import com.example.netologyandroidhomework1.databinding.PostBinding
import com.example.netologyandroidhomework1.model.Post
import com.example.netologyandroidhomework1.utills.ConverterCountFromIntToString


class PostHolder(
    val context: Context,
    private val binding: PostBinding,
    val listener: OnButtonTouchListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(post: Post) {
        val likeButton = binding.like
        val shareButton = binding.share
        val valueInDp = 64
        val valueInPx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, valueInDp.toFloat(), context.resources.displayMetrics
        ).toInt()
        binding.avatar.apply {
            if(post.authorAvatar!=null){
                setImageBitmap(
                    BitmapFactory.decodeStream(context.openFileInput(post.authorAvatar)).scale(
                        valueInPx,valueInPx,true
                    )
                )
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