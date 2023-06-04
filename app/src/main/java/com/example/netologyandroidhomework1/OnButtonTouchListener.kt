package com.example.netologyandroidhomework1

interface OnButtonTouchListener {
    fun onLikeCLick(id:Int)
    fun onShareCLick(id:Int)
    fun onRemoveClick(id:Int)
    fun onUpdateCLick(id:Int,oldContent:String)
    fun onCreateClick()
}