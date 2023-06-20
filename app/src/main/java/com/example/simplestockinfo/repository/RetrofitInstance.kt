package com.example.simplestockinfo.repository

import com.example.simplestockinfo.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor


object RetrofitInstance {

    // 통신을 확인하기 위해 제 서버주소를 넣었습니다.
    private const val SERVER_URL = "https://www.koreaexim.go.kr/site/program/financial/"
    // 요청문제만 어떻게 해결하면 될거같은데
    private val gson : Gson = GsonBuilder().setLenient().create()
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(SERVER_URL)
//            .client(buildOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private const val SERVER_URL2 = "https://commodities-api.com/api/"
    // 요청문제만 어떻게 해결하면 될거같은데
    private val retrofit_wti by lazy {
        Retrofit.Builder()
            .baseUrl(SERVER_URL2)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
    private const val SERVER_URL3 = "https://api.twitter.com/2/users/"
    // 요청문제만 어떻게 해결하면 될거같은데
    private val retrofit_tweet by lazy {
        Retrofit.Builder()
            .client(buildOkHttpClient())
            .baseUrl(SERVER_URL3)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
    fun buildOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }
    val api : RetrofitApi by lazy { retrofit.create(RetrofitApi::class.java) }
    val api_wti : RetrofitApi by lazy { retrofit_wti.create(RetrofitApi::class.java) }
    val api_tweet : RetrofitApi by lazy { retrofit_tweet.create(RetrofitApi::class.java) }
}