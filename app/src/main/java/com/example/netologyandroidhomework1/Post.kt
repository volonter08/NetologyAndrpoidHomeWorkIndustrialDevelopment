package com.example.netologyandroidhomework1

data class Post(
    private var countLiked: Int = 0,
    private var countShared: Int = 0,
) {
    var isLiked: Boolean = false
    lateinit var textCountLiked:String
    lateinit var textCountShared:String
    init {
        convertCountLikedToString()
        convertCountSharedToString()
    }
    fun like() {
        if (!isLiked) {
            countLiked++
            convertCountLikedToString()
            isLiked = true
        }
        convertCountLikedToString()
    }
    fun dislike() {
        if (isLiked) {
            countLiked--
            convertCountLikedToString()
            isLiked = false
        }
    }

    fun share() {
        countShared++
        convertCountSharedToString()
    }
    private fun convertCountLikedToString(){
        textCountLiked = when(countLiked){
            in (0 until 1000) -> countLiked.toString()
            in (1000 until 1100)-> ((countLiked/1000)).toString() + "K"
            in (1100 until 10_000)-> ((countLiked - countLiked%100).toFloat()/1000).toString() + "K"
            in (10_000 until 1000_000)-> ((countLiked)/1000).toString() + "K"
            in (1000_000 until 1100_000)-> ((countLiked/1000_000)).toString() + "M"
            in (1100_000 until 10_000_000)-> ((countLiked - countLiked%100_000).toFloat()/1000_000).toString() + "M"
            in (10_000_000 until 1000_000_000)-> ((countLiked/1000_000)).toString() + "M"
            else -> ">1B"
        }
    }
    private fun convertCountSharedToString(){
        textCountShared = when(countShared){
            in (0 until 1000) -> countShared.toString()
            in (1000 until 1100)-> ((countShared/1000)).toString() + "K"
            in (1100 until 10_000)-> ((countShared - countShared%100).toFloat()/1000).toString() + "K"
            in (10_000 until 1000_000)-> ((countShared)/1000).toString() + "K"
            in (1000_000 until 1100_000)-> ((countShared/1000_000)).toString() + "M"
            in (1100_000 until 10_000_000)-> ((countShared - countShared%100_000).toFloat()/1000_000).toString() + "M"
            in (10_000_000 until 1000_000_000)-> ((countShared/1000_000)).toString() + "M"
            else -> ">1B"
        }
    }
}