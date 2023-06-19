package com.example.simplestockinfo.fragmant

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.simplestockinfo.MainViewModel
import com.example.simplestockinfo.R
import com.example.simplestockinfo.repository.Repository
import com.example.simplestockinfo.ui.theme.AppTheme
import com.example.simplestockinfo.ui.theme.MainViewModelFactory


@Composable
fun MainScreen(viewModel: MainViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        val wtiData = viewModel.wtiData.observeAsState()
        val exchangeRateData = viewModel.exchangeRateData.observeAsState()
        val nasdaqData = viewModel.nasdaqData.observeAsState()
        Text(
            text = "Simple Stock",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, top = 10.dp)
                .clickable { viewModel.sendSocketData() },
            textAlign = TextAlign.Left,
            color = MaterialTheme.colorScheme.onBackground,
            fontFamily = FontFamily.Serif,
            fontSize = TextUnit(22f, TextUnitType.Sp)
        )

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp),){
            item {
                InfoHead(wtiData.value,exchangeRateData.value,nasdaqData.value)
            }
            items(20){
                TwitterCard()
            }
        }

    }
}

class MainScreenFragment : Fragment() {
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
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        return ComposeView(requireContext()).apply {
            // Dispose of the Composition when the view's LifecycleOwner
            // is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                AppTheme(true) {
                    MainScreen(viewModel)
                }
            }
        }

    }

}

@Composable
fun InfoHead(wti:String?,exchangeRate:String?,nasdaq:String?){
    Row(modifier = Modifier
        .height(100.dp)
        .padding(horizontal = 10.dp)
        .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
        Card(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            shape = RoundedCornerShape(17.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Row(modifier = Modifier.fillMaxSize(), verticalAlignment = CenterVertically) {
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    painter = painterResource(R.drawable.baseline_currency_exchange_24),
                    "환율 아이콘"
                )
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(text = "${exchangeRate}\nKRW", modifier = Modifier.align(Center))
                }
            }
        }
        Spacer(modifier = Modifier.width(10.dp))
        Card(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            shape = RoundedCornerShape(17.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Row(modifier = Modifier.fillMaxSize(), verticalAlignment = CenterVertically) {
                Spacer(modifier = Modifier.width(10.dp))
                Icon(painter = painterResource(R.drawable.baseline_water_drop_24), "WTI 아이콘")
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(text = "$wti\nUSD", modifier = Modifier.align(Center))
                }
            }
        }
        Spacer(modifier = Modifier.width(10.dp))
        Card(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            shape = RoundedCornerShape(17.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
        ) {
            Row(modifier = Modifier.fillMaxSize(), verticalAlignment = CenterVertically) {
                Spacer(modifier = Modifier.width(10.dp))
                Icon(painter = painterResource(R.drawable.baseline_trending_up_24), "나스닥 아이콘")
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(text = "$nasdaq", modifier = Modifier.align(Center))
                }
            }
        }
    }
}

@Composable
fun TwitterCard(){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .height(200.dp),
        shape = RoundedCornerShape(17.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
    ) {
        Text(text = "Twitter",Modifier.padding(10.dp))
    }
}