package com.example.simplestockinfo
// view를 만들어야 하기에 각 정보별 틀을 여기서 만들어야 됨
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.simplestockinfo.model.tweetdata.Data
import com.example.simplestockinfo.repository.Repository
import com.example.simplestockinfo.ui.theme.MainViewModelFactory
import com.example.simplestockinfo.ui.theme.SimpleStockInfoTheme
import kotlinx.coroutines.*

class MainActivity : ComponentActivity() {
    lateinit var viewModel: MainViewModel
    lateinit var viewModelFactory: MainViewModelFactory
    private var repository = Repository()
    private var Name = mutableListOf<String>()
    @SuppressLint("CoroutineCreationDuringComposition")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this,viewModelFactory).get(MainViewModel::class.java)
        setContent {
            SimpleStockInfoTheme {
                Column{
                    InfoCard(viewModel)
                    Spacer(modifier = Modifier.height(5.dp))
                    InfoCard2(mainViewModel = viewModel)
                    Conversation(mainViewModel = viewModel)

                }
                }
            }

    }
}
// 이제 나스닥 선물 하고 트윛터만 가져와서 붙이면 완성!!!
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun InfoCard(mainViewModel: MainViewModel){
    val Name by mainViewModel.getData().observeAsState()
    Log.d("TAG", Name.toString())
    Name?.let { it ->

        Row(modifier = Modifier
            .fillMaxWidth()
            .background(shape = RoundedCornerShape(6.dp), color = Color.LightGray)
            .padding(30.dp)
            .height(40.dp),
            verticalAlignment = Alignment.CenterVertically

        ) {
            Spacer(modifier = Modifier.height(15.dp))
            Text(it.get(0), modifier = Modifier.width(200.dp), textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.width(30.dp))
            Text(text = it.get(1))
        }
    }
}
@Composable
fun InfoCard2(mainViewModel: MainViewModel){
    val wti by mainViewModel.getWTI().observeAsState()
    wti?.let { it ->
        Row(modifier = Modifier
            .fillMaxWidth()
            .background(shape = RoundedCornerShape(6.dp), color = Color.Magenta)
            .padding(30.dp)
            .height(40.dp),
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
        Row(modifier = Modifier.padding(all = 8.dp)) {

            Column() {
                Text(
                    text = author,

                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Surface(

            ) {
                Text(
                    text = data.text,
                    modifier = Modifier.padding(all = 4.dp),

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
        LazyColumn(modifier = Modifier
            .verticalScroll(listState)
            .height(500.dp)) {
            items(messages) { message ->
                MessageCard(author, message)
            }
        }

    }
// 여기서 요청받은 애의 이름과 데이터를 뽑아서 바인딩

}
