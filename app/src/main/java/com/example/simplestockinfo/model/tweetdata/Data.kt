package com.example.simplestockinfo.model.tweetdata


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("edit_history_tweet_ids")
    val editHistoryTweetIds: List<String>,
    val id: String,
    val text: String
)