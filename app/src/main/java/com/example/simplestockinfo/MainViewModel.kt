package com.example.simplestockinfo

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.example.simplestockinfo.Service.newsService
import com.example.simplestockinfo.model.tweetdata.Data
import com.example.simplestockinfo.repository.Repository
import com.example.simplestockinfo.Service.TweetService
import com.example.simplestockinfo.model.tweetdata.TweetTimeLine
import com.example.simplestockinfo.repository.NewsRepository
import com.example.simplestockinfo.repository.SocketRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

// factory pattern 으로 usecase를 양산화 시킬것
class MainViewModel(
    private val repository: Repository,
    private val socketRepository: SocketRepository
) : ViewModel() {
    // ui에 들어갈 string을 저장할 리스트를 하나 만듬
    val output: MutableLiveData<ArrayList<String>> = MutableLiveData()
    var tweetService: TweetService = TweetService(repository = repository)
    lateinit var newsService: newsService
    lateinit var wti_output: MutableLiveData<Pair<String, Double>>
    var tweetLivedata = MutableLiveData<Pair<String, List<Data>>>()

    var wtiData: LiveData<String> = socketRepository.getWtiData().asLiveData()
    var exchangeRateData: LiveData<String> = socketRepository.getExchangeRateData().asLiveData()
    var nasdaqData: LiveData<String> = socketRepository.getNasdaqData().asLiveData()

    private val _tweet: MutableLiveData<TweetTimeLine> = MutableLiveData()
    val tweet: LiveData<TweetTimeLine> get() = _tweet

    private val TAG = "MainViewModel"

    @RequiresApi(Build.VERSION_CODES.O)
    /// 환율
    fun getData(): MutableLiveData<ArrayList<String>> {
        viewModelScope.launch {
            repository.apiGetInfoData()?.run {
                if (this.isEmpty()) {
                    return@run
                }
                output.value = arrayListOf(this.get(22).curNm, this.get(22).kftcDealBasR)
                Log.d("TAG", output.value.toString())
            }

        }
        Log.d("TAG", output.value.toString())
        return output
    }

    fun getWTI(): MutableLiveData<Pair<String, Double>> {
        viewModelScope.run {
            wti_output = MutableLiveData()
            Log.d("TAG", repository.getWTI().toString())
            wti_output = repository.getWTI()
        }
        return wti_output
    }

    fun getTweet(): MutableLiveData<Pair<String, List<Data>>> {
        tweetService = TweetService(repository)
        viewModelScope.launch {
            tweetLivedata.value = tweetService.getTweetTimeLine().value
        }
        Log.d("DAD2", tweetLivedata.value?.second.toString())
        return tweetLivedata
    }

    fun getNewsData(): MutableLiveData<Pair<String, List<Data>>> {
        // NewsService
        newsService = newsService(NewsRepository());
        return MutableLiveData()

    }

    fun sendSocketData() {
        socketRepository.sendMsg()
    }

    fun getTwit(){
        viewModelScope.launch {
            tweetService.getTweet()
                .catch {
                    Log.d(TAG, "getTwit: catch")
                    Log.d(TAG, "getTwit: ${it.toString()}")
                }.collectLatest {
                    Log.d(TAG, "getTwit: collect latest")
                    _tweet.postValue(it)
                }
        }
    }


}