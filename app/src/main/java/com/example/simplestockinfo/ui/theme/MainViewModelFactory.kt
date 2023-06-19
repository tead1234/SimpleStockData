package com.example.simplestockinfo.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.simplestockinfo.MainViewModel
import com.example.simplestockinfo.Service.SocketService
import com.example.simplestockinfo.repository.Repository
import com.example.simplestockinfo.repository.SocketRepository

class MainViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(Repository(), SocketRepository(SocketService)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }

}
