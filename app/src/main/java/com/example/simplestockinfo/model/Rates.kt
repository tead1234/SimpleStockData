package com.example.simplestockinfo.model


import com.google.gson.annotations.SerializedName

data class Rates(
    @SerializedName("USD")
    val uSD: Int,
    @SerializedName("WTIOIL")
    val wTIOIL: Double
)