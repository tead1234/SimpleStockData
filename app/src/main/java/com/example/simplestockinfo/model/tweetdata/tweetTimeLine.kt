package com.example.simplestockinfo.model.tweetdata


import com.google.gson.annotations.SerializedName

data class tweetTimeLine(
    val `data`: List<Data>,
    val meta: Meta
)