package com.example.netologyandroidhomework1

import android.annotation.SuppressLint
import android.content.Intent
import android.icu.text.DateTimePatternGenerator.PatternInfo.OK
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.view.View.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.netologyandroidhomework1.adapter.PostAdapter
import com.example.netologyandroidhomework1.databinding.ActivityMainBinding
import com.example.netologyandroidhomework1.model.Post
import com.example.netologyandroidhomework1.viewModel.PostViewModel
import androidx.activity.result.launch
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val viewBinding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        val viewModel: PostViewModel by viewModels()
        val newPostLauncher = registerForActivityResult(NewPostActivityContract()){
            if(it==null){
                Snackbar.make(viewBinding.root,"Контент не может быть пустым",Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok){

                }.show()
            }
            else
            viewModel.createPost(it)
        }
        val editPostLauncher = registerForActivityResult(EditPostActivityContract()){post->
            if(post==null){
                Snackbar.make(viewBinding.root,"Контент не может быть пустым",Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok){

                }.show()
            }
            else
            viewModel.update(post)
        }
        val postOnButtonTouchListener = object : OnButtonTouchListener {
            override fun onLikeCLick(id: Int) =
                viewModel.likeOrDislike(id)
            override fun onShareCLick(post: Post){
                val intentSend = Intent().apply {
                    action= Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT,post.content)
                    type = "text/plain"
                }
                val chooserIntentSend = Intent.createChooser(intentSend, "getString(R.string.)")
                startActivity(chooserIntentSend)
                viewModel.share(post.id)
            }
            override fun onRemoveClick(id: Int) =
                viewModel.remove(id)
            override fun onUpdateCLick(post: Post) {
                editPostLauncher.launch(post)
            /*viewBinding.editText.let { editText ->
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

                 */
            }
            override fun onCreateClick() {
                newPostLauncher.launch()
            }

            override fun onStartVideo(post: Post) {
                val intent = Intent(Intent.ACTION_VIEW,Uri.parse(post.videoUrl) )
                startActivity(intent)
            }
        }
        val postAdapter = PostAdapter(postOnButtonTouchListener)
        viewBinding.recycleView.adapter = postAdapter
        viewModel.data.observe(this) { posts ->
            postAdapter.submitList(posts)
        }
        viewBinding.createButton.setOnClickListener {
            postOnButtonTouchListener.onCreateClick()
        }
    }
}

