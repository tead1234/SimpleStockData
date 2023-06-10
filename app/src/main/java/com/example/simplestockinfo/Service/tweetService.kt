package com.example.simplestockinfo.Service

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.simplestockinfo.model.tweetdata.Data
import com.example.simplestockinfo.repository.Repository
import com.example.simplestockinfo.repository.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class tweetService(private var  repository: Repository) {
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
}