package com.example.simplestockinfo.model.tweetdata


import com.google.gson.annotations.SerializedName

data class Meta(
    @SerializedName("newest_id")
    val newestId: String,
    @SerializedName("next_token")
    val nextToken: String,
    @SerializedName("oldest_id")
    val oldestId: String,
    @SerializedName("result_count")
    val resultCount: Int
)