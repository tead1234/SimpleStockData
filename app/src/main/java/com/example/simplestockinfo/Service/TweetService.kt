package com.example.simplestockinfo.Service

import android.util.Log
import androidx.lifecycle.MutableLiveData
import blue.starry.penicillin.PenicillinClient
import blue.starry.penicillin.core.emulation.EmulationMode
import blue.starry.penicillin.core.session.config.account
import blue.starry.penicillin.core.session.config.api
import blue.starry.penicillin.core.session.config.application
import blue.starry.penicillin.core.session.config.token
import blue.starry.penicillin.endpoints.common.TweetMode
import com.example.simplestockinfo.BuildConfig
import com.example.simplestockinfo.model.tweetdata.Data
import com.example.simplestockinfo.model.tweetdata.TweetTimeLine
import com.example.simplestockinfo.repository.Repository
import com.example.simplestockinfo.repository.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.concurrent.TimeUnit

class TweetService(private var  repository: Repository) {

    val client = PenicillinClient {
        // Configures about your credentials.
        account {
            application(BuildConfig.API_KEY, BuildConfig.API_SECRET)
            // Official clients are pre-defined.
            // application(OfficialClient.OAuth1a.TwitterForiPhone)

            // For OAuth 1.0a
            token(BuildConfig.ACCESS_KEY, BuildConfig.ACCESS_SECRET)
            // For OAuth 2.0
            // token("BearerToken")
        }

        // Configures about API connection.
        api {
            // Pretends "Twitter for iPhone". All the requests are based on "Twitter for iPhone".
            // It is required when you'd like to access some Private APIs (such as Polling Tweets).
//            emulationMode = EmulationMode.TwitterForiPhone
            defaultTweetMode = TweetMode.Default
        }
    }


    suspend fun getTweetTimeLine(): MutableLiveData<Pair<String, List<Data>>> {
//        var items = HashMap<String, String>()
        var tweetLivedata = MutableLiveData<Pair<String, List<Data>>>()
//        items["Authorization"] = "Bearer AAAAAAAAAAAAAAAAAAAAANh4iQEAAAAASdZZtK3AUI8DQPevEfqsThR%2Brro%3DIbaaq0daT9QbhbvEomiRQtHG8O8z0QUJkypsJ0BnT0X0itADxO"
//        items["Content-Type"] = "application/json"


        var AUTHOR = "Breaking News"
        // 아이디를 기준으로 트윗데이터를 받아오고
        // 여기선 그냥 데이터 값만 보내주는것만 구현
        // 데이터를 관찰하도록 짜는 건 viewmodel에서 구현
        // 아이디랑 텍스트
        // tweeter text를 5개 받아 오니 이걸 전부 리스트에 저장
        CoroutineScope(Dispatchers.IO).run {
            var response = RetrofitInstance.api_tweet.getTweetTimeLine(5)
            if (response.isSuccessful) {
                Log.d("연결안됨",response.body().toString())
                response.body().let {
                    if (it != null){
                        tweetLivedata.value =
                            Pair<String, List<Data>>(AUTHOR, response.body()!!.data)
                        Log.d("연결안됨",tweetLivedata.value.toString())
                        return tweetLivedata
                    }
                    else{

                        null
                    }
                }

            }
            else{
                Log.d("연결안됨","연결안됨")
            }


        }
        return tweetLivedata
    }

    fun getTweet(): Flow<TweetTimeLine> = flow {
        val response = RetrofitInstance.api_tweet.getTweetTimeLine(5)
        if(response.isSuccessful) {
            kotlin.runCatching {
                response.body()?:throw RuntimeException("invalid JSON File.")
            }.onSuccess {
                emit(it)
            }.onFailure {
                throw RuntimeException("invalid JSON File.")
            }
        } else {
            throw RuntimeException("response is Failed.")
        }
    }
}