package com.example.netologyandroidhomework1

import android.opengl.Visibility
import android.os.Bundle
import android.view.View.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.netologyandroidhomework1.adapter.PostAdapter
import com.example.netologyandroidhomework1.databinding.ActivityMainBinding
import com.example.netologyandroidhomework1.viewModel.PostViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val viewBinding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        val viewModel: PostViewModel by viewModels()
        val postOnButtonTouchListener = object : OnButtonTouchListener {
            override fun onLikeCLick(id: Int) =
                viewModel.likeOrDislike(id)

            override fun onShareCLick(id: Int) =
                viewModel.share(id)

            override fun onRemoveClick(id: Int) =
                viewModel.remove(id)

            override fun onUpdateCLick(id: Int, oldContent: String) {
                viewBinding.editText.let { editText ->
                    viewBinding.linearLayoutUpdate.visibility = VISIBLE
                    viewBinding.updateContentText.text = oldContent
                    viewBinding.save.apply {
                        setImageResource(R.drawable.baseline_check_circle_24)
                        setOnClickListener {
                            viewBinding.linearLayoutUpdate.visibility = GONE
                            viewBinding.editText.let {
                                viewBinding.editText.clearFocus()
                                AndroidUtils.hideKeyBoard(viewBinding.editText)
                                viewModel.update(id, newContent = it.text.toString())
                                it.text.clear()
                                setImageResource(R.drawable.baseline_create_24)
                                setOnClickListener {
                                    onCreateClick()
                                }
                            }
                        }
                    }
                    AndroidUtils.showKeyBoard(editText)
                    editText.setText(oldContent)
                }
            }

            override fun onCreateClick() {
                viewBinding.editText.let {
                    viewModel.createPost(it.text.toString())
                    viewBinding.editText.clearFocus()
                    AndroidUtils.hideKeyBoard(viewBinding.editText)
                    it.text.clear()
                }
            }
        }
        val postAdapter = PostAdapter(postOnButtonTouchListener)
        viewBinding.recycleView.adapter = postAdapter
        viewModel.data.observe(this) { posts ->
            postAdapter.submitList(posts)
        }
        viewBinding.save.setOnClickListener {
            postOnButtonTouchListener.onCreateClick()
        }
        viewBinding.editText.setOnFocusChangeListener { v, hasFocus ->
            viewBinding.save.visibility =
                if (hasFocus)
                    VISIBLE
                else
                    GONE
        }
        viewBinding.cancelButton.setOnClickListener {
            viewBinding.linearLayoutUpdate.visibility = GONE
            viewBinding.editText.text.clear()
            viewBinding.save.setImageResource(R.drawable.baseline_create_24)
            viewBinding.save.setOnClickListener {
                postOnButtonTouchListener.onCreateClick()
            }
        }
    }
}