package com.example.simplestockinfo.Fragmant

import android.content.Context
import android.os.Build
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.databinding.DataBindingUtil

import com.example.simplestockinfo.MainViewModel
import com.example.simplestockinfo.R
import com.example.simplestockinfo.databinding.ScreenSlidePageBinding
import androidx.compose.material3.MaterialTheme
import com.example.simplestockinfo.ui.theme.SimpleStockInfoTheme
import androidx.lifecycle.ViewModelProvider
import com.example.simplestockinfo.repository.Repository
import com.example.simplestockinfo.ui.theme.MainViewModelFactory


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
                Column{
                    InfoCard(viewModel)
                }
                  
              }
          }

        return view

    }
//
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
}