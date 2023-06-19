package com.example.simplestockinfo.Service

import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object SocketService {

    private const val TAG = "SocketService"

    private val socket: Socket = IO.socket("http://192.168.0.6:3000")

    val connected = socket.connected()

    suspend fun connect() {

        socket.connect()

        return suspendCoroutine {
            socket.once(Socket.EVENT_CONNECT) { _ ->
                it.resume(Unit)
            }
        }
    }

    suspend fun disconnect() {

        socket.disconnect()

        return suspendCoroutine {
            socket.once(Socket.EVENT_DISCONNECT) { _ ->
                it.resume(Unit)
            }
        }
    }

    fun onWtiDataReceived() = callbackFlow {
        socket.on("wti") {
            trySend(it[0].toString())
        }
        awaitClose()
    }

    fun onExchangeRateDataReceived() = callbackFlow {
        socket.on("exchangeRate") {
            trySend(it[0].toString())
        }
        awaitClose()
    }

    fun onNasdaqDataReceived() = callbackFlow {
        socket.on("nasdaq") {
            trySend(it[0].toString())
        }
        awaitClose()
    }

    fun sendMsg() {
        socket.emit("msg", "MSG FROM ANDROID")
    }
}