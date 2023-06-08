package com.example.simplestockinfo

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplestockinfo.Service.newsService
import com.example.simplestockinfo.model.tweetdata.Data
import com.example.simplestockinfo.repository.Repository
import com.example.simplestockinfo.Service.tweetService
import com.example.simplestockinfo.repository.NewsRepository
import kotlinx.coroutines.launch

// factory pattern 으로 usecase를 양산화 시킬것
class MainViewModel (private val repository: Repository):ViewModel() {
    // ui에 들어갈 string을 저장할 리스트를 하나 만듬
    val output: MutableLiveData<ArrayList<String>> = MutableLiveData()
    lateinit var tweetService: tweetService
    lateinit var newsService: newsService
    lateinit var wti_output  :  MutableLiveData<Pair<String,Double>>
    var tweetLivedata = MutableLiveData<Pair<String,List<Data>>>()
    @RequiresApi(Build.VERSION_CODES.O)
    fun getData(): MutableLiveData<ArrayList<String>> {
        viewModelScope.launch {
            repository.apiGetInfoData()?.run {

                output.value = arrayListOf(this.get(22).curNm, this.get(22).kftcDealBasR)
                Log.d("TAG",output.value.toString())
            }

        }
        Log.d("TAG",output.value.toString())
        return output
    }

    fun getWTI(): MutableLiveData<Pair<String,Double>> {
        viewModelScope.run {
            wti_output = MutableLiveData()
            Log.d("TAG",repository.getWTI().toString())
            wti_output = repository.getWTI()
        }
        return wti_output
    }

     fun getTweet(): MutableLiveData<Pair<String, List<Data>>> {
            tweetService = tweetService(repository)
            viewModelScope.launch {
            tweetLivedata.value = tweetService.getTweetTimeLine().value
            }
         Log.d("DAD2",tweetLivedata.value?.second.toString())
            return tweetLivedata
    }

    fun getNewsData(): MutableLiveData<Pair<String,List<Data>>>{
        // NewsService
        newsService = newsService(NewsRepository());
        return MutableLiveData()

    }



}