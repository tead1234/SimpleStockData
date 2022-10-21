package com.example.simplestockinfo

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplestockinfo.model.tweetdata.Data
import com.example.simplestockinfo.repository.Repository
import com.example.simplestockinfo.useCase.tweetUseCase
import kotlinx.coroutines.launch
import java.util.function.DoubleToLongFunction

class MainViewModel (private val repository: Repository):ViewModel() {
    // ui에 들어갈 string을 저장할 리스트를 하나 만듬
    val output: MutableLiveData<ArrayList<String>> = MutableLiveData()
    lateinit var tweetUseCase: tweetUseCase
    lateinit var wti_output  :  MutableLiveData<Pair<String,Double>>
    @RequiresApi(Build.VERSION_CODES.O)
    fun getData(): MutableLiveData<ArrayList<String>> {
        viewModelScope.launch {
            repository.apiGetInfoData()?.apply {
                output.value = arrayListOf(this.get(22).curNm, this.get(22).kftcDealBasR)
            }

        }
        return output
    }

    fun getWTI(): MutableLiveData<Pair<String,Double>> {
        viewModelScope.run {
            wti_output = MutableLiveData()
            Log.d("TAG",repository.getWTI().toString())
            wti_output = repository.getWTI()

//            repository.getWTI().run {
//                if (this.isEmpty()){
//                    Log.d("DDD", "비어있음")
//                }
//                var fi = this.get(0)
//                wti_output = MutableLiveData()
//                wti_output.value = fi
//
//            }
        }
        return wti_output
    }
    fun getTweet(): MutableLiveData<Pair<String, List<Data>>> {
            tweetUseCase = tweetUseCase(repository)
            var tweetLivedata = tweetUseCase.getTweetTimeLine(381696140)
            return tweetLivedata
    }

}