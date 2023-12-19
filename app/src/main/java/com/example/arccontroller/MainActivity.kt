package com.example.arccontroller

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.arccontroller.ui.theme.ARCControllerTheme


class MainActivity : ComponentActivity(), MessageCallback {

    private val serverUri = "tcp://142.128.4.1:1883"
    private val clientId = "AndroidClient123753"
    private val topics = listOf("stepper/cmd","can_vel/cmd")

    var linearX : String by mutableStateOf("123")
    var angularZ : String by mutableStateOf("3")
    var nGrooves : String by mutableStateOf("4")
    var stepperPitch : String by mutableStateOf("2")

    private lateinit var manager: MqttManager
    private var chatMessages by mutableStateOf(emptyList<String>())

    override fun onCreate(savedInstanceState: Bundle?) {

        manager = MqttManager(applicationContext, serverUri, clientId, topics, this)
        super.onCreate(savedInstanceState)
        setContent {
            ARCControllerTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SetScreen(linearX,angularZ,nGrooves,stepperPitch,pubFun = ::sendMessage)
                }

            }
        }
    }

    override fun onMessageReceived(topic : String, message: String) {

        if (topic == "stepper/cmd"){
            linearX = message
        }
        else if (topic == "drive/cmd"){
            angularZ = message
        }
        else if (topic == "cmd/vel3"){
            nGrooves = message
        }
        else if (topic == "cmd/vel4"){
            stepperPitch = message
        }

        Log.d(topic,message)
    }
    private fun sendMessage(topic: String, message: String) {

        if (manager.client.isConnected) {
            manager.publish(topic,message)
        }
    }

}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ARCControllerTheme {
        //MainGui()
    }
}