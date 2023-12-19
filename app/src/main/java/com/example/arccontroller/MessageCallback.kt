package com.example.arccontroller

interface MessageCallback {
    fun onMessageReceived(topic : String, message: String)
}
