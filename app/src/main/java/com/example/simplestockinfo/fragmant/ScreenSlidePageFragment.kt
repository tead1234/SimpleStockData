package com.example.simplestockinfo.fragmant

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.simplestockinfo.MainViewModel
import com.example.simplestockinfo.MessageCard
import com.example.simplestockinfo.R
import com.example.simplestockinfo.databinding.ScreenSlidePageBinding
import com.example.simplestockinfo.model.tweetdata.Data
import com.example.simplestockinfo.repository.Repository
import com.example.simplestockinfo.ui.theme.MainViewModelFactory
import com.example.simplestockinfo.ui.theme.SimpleStockInfoTheme
import com.google.accompanist.web.AccompanistWebChromeClient
import com.google.accompanist.web.AccompanistWebViewClient

private val webViewClient = AccompanistWebViewClient()
private val webChromeClient = AccompanistWebChromeClient()
class ScreenSlidePageFragment : Fragment() {
    lateinit var viewModel: MainViewModel
    lateinit var viewModelFactory: MainViewModelFactory
    private var repository = Repository()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this,viewModelFactory).get(MainViewModel::class.java)
        val binding = DataBindingUtil.inflate<ScreenSlidePageBinding>(
            inflater,  R.layout.screen_slide_page, container, false)
        val view = binding.root
        binding.slideComposeView.apply{
              setContent {
                  Column(modifier = Modifier
                      .fillMaxWidth()
                      .fillMaxHeight(0.3f)) 
                  {
                      WebViewComposable()
                      InfoCard(viewModel)
                      InfoCard2(mainViewModel = viewModel)
                      Conversation(mainViewModel = viewModel)
                  }
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White)
                    .border(5.dp, color = Color.Cyan, RoundedCornerShape(16.dp))
                    .fillMaxHeight(0.4f))
                {

                }

                  
              }
          }

        return view

    }
//
}
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewComposable() {

    AndroidView(factory = { context ->
        WebView(context).apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
            loadUrl("https://sslecal2.investing.com?ecoDayBackground=%23b3b3b3&columns=exc_flags,exc_currency,exc_importance,exc_actual,exc_forecast&features=datepicker,timezone&countries=25,32,6,37,72,22,17,39,14,10,35,43,56,36,110,11,26,12,4,5&calType=day&timeZone=88&lang=1")
        }
    }) {
        it.webViewClient = WebViewClient()
        it.loadUrl("https://sslecal2.investing.com?ecoDayBackground=%23b3b3b3&columns=exc_flags,exc_currency,exc_importance,exc_actual,exc_forecast&features=datepicker,timezone&countries=25,32,6,37,72,22,17,39,14,10,35,43,56,36,110,11,26,12,4,5&calType=day&timeZone=88&lang=1")

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun InfoCard(mainViewModel: MainViewModel){
    val Name by mainViewModel.getData().observeAsState()
    Log.d("TAG", Name.toString())
    Name?.let { it ->

        SimpleStockInfoTheme{
            Row(modifier = Modifier
                .fillMaxWidth()
                .background(shape = RoundedCornerShape(10.dp), color = Color.White)
                .border(2.dp, color = Color.LightGray, RoundedCornerShape(16.dp))
                .padding(30.dp)
                .wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically

            ) {
                Spacer(modifier = Modifier.height(15.dp))
                Text(it.get(0), modifier = Modifier.width(200.dp), textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.width(15.dp))
                Text(text = it.get(1))
            }
        }

    }
}
@Composable
fun InfoCard2(mainViewModel: MainViewModel){
    val wti by mainViewModel.getWTI().observeAsState()
    wti?.let { it ->
        Row(modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .border(width = 2.dp, color = Color.LightGray, RoundedCornerShape(16.dp))
            .padding(30.dp)
            .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically

        ) {
            Text(text = it.first, modifier = Modifier.width(200.dp), textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.width(30.dp))
            var wti =  1 / it.second!!
            Text(text = String.format("%2f", wti))
        }
    }
}

@Composable
fun MessageCard(author: String, data: Data) {
    Row(modifier = Modifier
        .padding(all = 8.dp)
        .background(color = Color(51,255,255))
        .border(2.dp, color = Color(51,255,255), RoundedCornerShape(16.dp))
    ) {
        Surface(
            shadowElevation = 1.dp,
        ) {
            Column() {

                Text(
                    text = author,
//                    MaterialTheme.typography.titleMedium,
                    color = Color.Black
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))

        Surface(
            shadowElevation = 1.dp,
        ) {
            Text(
                text = data.text,
                modifier = Modifier.padding(all = 4.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
        }
    }
}

@Composable
fun Conversation(mainViewModel: MainViewModel) {


    val stat by mainViewModel.getTweet().observeAsState()
    val listState = rememberScrollState()

    stat?.let {

        var author = it.first
        var messages = it.second
        Box(modifier = Modifier.height(200.dp)){
            Column(modifier = Modifier
                .verticalScroll(listState))
                 {
                messages.forEach {message ->
                    MessageCard(author = author, data = message)
                }

            }

        }
        }

}
// 여기서 요청받은 애의 이름과 데이터를 뽑아서 바인딩


