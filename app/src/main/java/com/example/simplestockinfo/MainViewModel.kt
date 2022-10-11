package com.example.simplestockinfo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplestockinfo.repository.Repository
import kotlinx.coroutines.launch

class MainViewModel (private val repository: Repository):ViewModel(){
    // ui에 들어갈 string을 저장할 리스트를 하나 만듬
    val output : MutableLiveData<ArrayList<String>> = MutableLiveData()
    fun getData() : MutableLiveData<ArrayList<String>> {
        viewModelScope.launch {
            repository.apiGetInfoData()?.apply{
                output.value = arrayListOf(this.get(22).curNm, this.get(9).dealBasR)
            }

        }
        return output
    }
    fun getWTI(): MutableLiveData<String> {

    }

}