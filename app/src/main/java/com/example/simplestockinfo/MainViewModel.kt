package com.example.simplestockinfo

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import blue.starry.penicillin.endpoints.common.TweetMode
import blue.starry.penicillin.endpoints.timeline
import blue.starry.penicillin.endpoints.timeline.userTimelineByScreenName
import blue.starry.penicillin.models.Status
import com.example.simplestockinfo.Service.TweetService
import com.example.simplestockinfo.Service.newsService
import com.example.simplestockinfo.model.tweetdata.Data
import com.example.simplestockinfo.repository.NewsRepository
import com.example.simplestockinfo.repository.Repository
import com.example.simplestockinfo.repository.SocketRepository
import kotlinx.coroutines.flow.flow
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
    var financialData: LiveData<String> = socketRepository.getFinancialData().asLiveData()
    var wtiData: LiveData<String> = socketRepository.getWtiData().asLiveData()
    var exchangeRateData: LiveData<String> = socketRepository.getExchangeRateData().asLiveData()
    var nasdaqData: LiveData<String> = socketRepository.getNasdaqData().asLiveData()

    val tweet: LiveData<List<Status>> = flow<List<Status>> {
        tweetService.client.use { client ->
            // Retrieve up to 100 tweets from @POTUS.
            val timeline =
                try {
                    client.timeline.userTimelineByScreenName(screenName = "markets", count = null, tweetMode = TweetMode.Default)
                        .execute()
                }catch (e:Exception){
                    emptyList()
                }

            emit(timeline.toList())


//        // The return value of the timeline is `JsonArrayResponse<Status>`. It implements `Iterable<T>`, which allows iterative operations.
//        timeline.forEach { status ->
//            // Print unescaped status text. If you want to get the raw html reference characters, you can use `textRaw`.
//            println(status.text)
//        }
        }
    }.asLiveData()


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

//    fun getFinancialData(): LiveData<String> {
//
//        return financialData
//    }

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


}