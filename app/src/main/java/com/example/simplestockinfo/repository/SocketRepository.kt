package com.example.simplestockinfo.repository

import com.example.simplestockinfo.Service.SocketService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SocketRepository(private val service: SocketService) {

    private val TAG = "SocketRepository"

    fun sendMsg() {
        service.sendMsg()
    }

    fun getWtiData(): Flow<String> {
        if (!service.connected) {
            CoroutineScope(Dispatchers.IO).launch {
                service.connect()
            }
        }
        return service.onWtiDataReceived()
    }

    fun getExchangeRateData(): Flow<String> {
        if (!service.connected) {
            CoroutineScope(Dispatchers.IO).launch {
                service.connect()
            }
        }
        return service.onExchangeRateDataReceived()
    }

    fun getNasdaqData(): Flow<String> {
        if (!service.connected) {
            CoroutineScope(Dispatchers.IO).launch {
                service.connect()
            }
        }
        return service.onNasdaqDataReceived()
    }

}