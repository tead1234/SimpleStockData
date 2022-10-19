package com.example.simplestockinfo.repository

import com.example.simplestockinfo.model.ExchangeInfoX
import com.example.simplestockinfo.model.WTIdata
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url


interface retrofitApi {

    @GET("exchangeJSON/?authkey=QERYtJ0Txoc4BEdZguQzAKUx5SILTc3l&searchdate=20221004&data=AP01")
//    suspend fun getData(@Path("authkey") authkey : String, @Path("searchdate") searchdate: String, @Path("data") data : String ) : Response<String>
    suspend fun getDa() : Response<ExchangeInfoX>
    @GET("latest")
    fun getWTI(@Query("access_key") access_key:String,@Query("base") base:String, @Query("symbols") symbols : String): Call<WTIdata>
}