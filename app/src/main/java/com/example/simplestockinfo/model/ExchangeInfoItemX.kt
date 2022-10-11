package com.example.simplestockinfo.model


import com.google.gson.annotations.SerializedName

data class ExchangeInfoItemX(
    val bkpr: String,
    @SerializedName("cur_nm")
    val curNm: String,
    @SerializedName("cur_unit")
    val curUnit: String,
    @SerializedName("deal_bas_r")
    val dealBasR: String,
    @SerializedName("kftc_bkpr")
    val kftcBkpr: String,
    @SerializedName("kftc_deal_bas_r")
    val kftcDealBasR: String,
    val result: Int,
    @SerializedName("ten_dd_efee_r")
    val tenDdEfeeR: String,
    val ttb: String,
    val tts: String,
    @SerializedName("yy_efee_r")
    val yyEfeeR: String
)