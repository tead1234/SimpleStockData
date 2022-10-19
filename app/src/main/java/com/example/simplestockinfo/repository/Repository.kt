package com.example.simplestockinfo.repository

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.simplestockinfo.model.ExchangeInfoX
import com.example.simplestockinfo.model.WTIdata
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// 일종의 어뎁터같은 느낌 여기서 api 요청을 처리함
// 여기서 만들어진거로 뷰모델에서 활용
class Repository() {
    private val API_KEY = "QERYtJ0Txoc4BEdZguQzAKUx5SILTc3l"
    private val API_KEY_WTI = "cl8xb7l3m8grfdi1w59tfaueom0z26r8al6eiac6qps189m976hxjvt7zsh2"
    private var out = MutableLiveData<Pair<String, Double>>()
    suspend fun apiGetInfoData(): ExchangeInfoX? {
//        val response= RetrofitInstance.api.getData(authkey = API_KEY, searchdate = "20221004" ,data="AP01" )
        val response = RetrofitInstance.api.getDa()
        return if(response.isSuccessful) response.body() as ExchangeInfoX else null
    }
    fun getWTI() : MutableLiveData<Pair<String, Double>> {
// 걍 response로 보내면 더 편한대 이걸로 처리하니깐 여기서 전부 수행해야돼서 망했네
        // 파라미터로 빈 배열을 받아와서 채우는 형식으로 짜볼자
        // 여기서 관찰하도록 mutable로 만들고
        RetrofitInstance.api_wti.getWTI(API_KEY_WTI,"USD","WTIOIL").enqueue(object: Callback<WTIdata> {
            override fun onFailure(call: Call<WTIdata>, t: Throwable) {
                Log.d("실패", t.toString())
            }

            override fun onResponse(call: Call<WTIdata>, response: Response<WTIdata>) {
                //todo 성공처리

                if(response.isSuccessful.not()){
                    Log.d("실패",response.body().toString())

                    return
                }
                // 아예 mutable Livedata로 보내주면 안되나??
                // api 데이터에서 필요한 애들만 리스트에 담아서 보내기

                response.body()?.let{

                    var wtidata = Pair<String, Double>(it.data.base, it.data.rates.WTIOIL)
                    Log.d("기름밧",wtidata.toString())
                    out.value = wtidata
                }

            }

        })
            return out

    }
//    fun getWTI2(): ArrayList<Pair<String, Double>> {
//        getWTI()
//        Log.d("TAG", out.toString())
//        return out
//    }

    companion object {
        private var instance: Repository? = null

        fun getInstance(): Repository? { // singleton pattern
            if (instance == null) instance = Repository()
            return instance
        }
    }
}


