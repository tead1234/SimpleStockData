package com.example.simplestockinfo.repository

import com.example.simplestockinfo.model.ExchangeInfoX
import com.example.simplestockinfo.model.WTIdata
import retrofit2.Call

// 일종의 어뎁터같은 느낌 여기서 api 요청을 처리함
// 여기서 만들어진거로 뷰모델에서 활용
class Repository() {
    private val API_KEY = "QERYtJ0Txoc4BEdZguQzAKUx5SILTc3l"
    private vall API_KEY_WTI =
    suspend fun apiGetInfoData(): ExchangeInfoX? {
//        val response= RetrofitInstance.api.getData(authkey = API_KEY, searchdate = "20221004" ,data="AP01" )
        val response = RetrofitInstance.api.getDa()
        return if(response.isSuccessful) response.body() as ExchangeInfoX else null
    }
    suspend fun getWTI() : WTIdata {
        val call = RetrofitInstance.api_wti.getWTI()

    }


    companion object {
        private var instance: Repository? = null

        fun getInstance(): Repository? { // singleton pattern
            if (instance == null) instance = Repository()
            return instance
        }
    }
}