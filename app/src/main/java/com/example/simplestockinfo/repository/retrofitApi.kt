package com.example.simplestockinfo.repository

import com.example.simplestockinfo.model.ExchangeInfoX
import com.example.simplestockinfo.model.WTIdata
import com.example.simplestockinfo.model.tweetdata.tweetTimeLine
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*





interface retrofitApi {

    @GET("exchangeJSON")
    suspend fun getDa(
        @Query("authkey") authkey: String,
        @Query("searchdate") searchdate: String,
        @Query("data") data: String
    ): Response<ExchangeInfoX>

    @GET("latest")
    fun getWTI(
        @Query("access_key") access_key: String,
        @Query("base") base: String,
        @Query("symbols") symbols: String
    ): Call<WTIdata>



    @GET("{id}/tweets")
    fun getTweetTimeLine(
        @HeaderMap headers2 : Map<String, String>,
        @Path("id") id: Int,
        @Query("max_results") max_results: Int
    ): Response<tweetTimeLine>
}