package com.example.simplestockinfo.model


import com.google.gson.annotations.SerializedName

data class Data(
    val base: String,
    val date: String,
    val rates: Rates,
    val success: Boolean,
    val timestamp: Int,
    val unit: String
)