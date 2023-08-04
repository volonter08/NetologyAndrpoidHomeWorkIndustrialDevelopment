package com.example.netologyandroidhomework1

import android.annotation.SuppressLint
import android.content.Intent
import android.icu.text.DateTimePatternGenerator.PatternInfo.OK
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.view.View.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.netologyandroidhomework1.adapter.PostAdapter
import com.example.netologyandroidhomework1.databinding.ActivityMainBinding
import com.example.netologyandroidhomework1.model.Post
import com.example.netologyandroidhomework1.viewModel.PostViewModel
import androidx.activity.result.launch
import androidx.fragment.app.commit
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        var flag = 0
        val viewBinding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        val viewModel: PostViewModel by viewModels()
        val newPostLauncher = registerForActivityResult(NewPostActivityContract()){
            if(it==null){
                Snackbar.make(viewBinding.root,"Контент не может быть пустым",Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok){

                }.show()
            }
            else {
                viewModel.createPost(it)
            }
        }
        val editPostLauncher = registerForActivityResult(EditPostActivityContract()){post->
            if(post==null){
                Snackbar.make(viewBinding.root,"Контент не может быть пустым",Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok){

                }.show()
            }
            else viewModel.update(post)
        }
        val postOnButtonTouchListener = object : OnButtonTouchListener {
            override fun onLikeCLick(id:Long) =
                viewModel.like(id)
            override fun onDislikeCLick(id: Long) {
                viewModel.dislike(id)
            }
            override fun onShareCLick(post: Post){
                val intentSend = Intent().apply {
                    action= Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT,post.content)
                    type = "text/plain"
                }
                val chooserIntentSend = Intent.createChooser(intentSend, "getString(R.string.)")
                startActivity(chooserIntentSend)
            }
            override fun onRemoveClick(id: Long) {
                viewModel.remove(id)
            }
            override fun onUpdateCLick(post: Post) {
                editPostLauncher.launch(post)
            }
            override fun onCreateClick() {
                newPostLauncher.launch()
            }
        }
        val postAdapter = PostAdapter(context = applicationContext,postOnButtonTouchListener)
        viewBinding.recycleView.adapter = postAdapter
        viewModel.data.observe(this) { feedModel ->
            feedModel.run {
                viewBinding.progressBar.visibility = if (loading) VISIBLE else GONE
                when{
                    error-> viewBinding.emtyOrErrorMessage.text = getString(R.string.error_message)
                    empty-> viewBinding.emtyOrErrorMessage.text = getString(R.string.empty_message)
                }
                viewBinding.emtyOrErrorMessage.visibility = if (error||empty) VISIBLE else GONE
                if (flag == 1) {
                 flag++
                }
                postAdapter.submitList(feedModel.posts)
            }
        }
        viewBinding.createButton.setOnClickListener {
            postOnButtonTouchListener.onCreateClick()
        }
    }
}

