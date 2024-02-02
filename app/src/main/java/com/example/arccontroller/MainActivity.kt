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
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class MainActivity : ComponentActivity(), MessageCallback {

    private val serverUri = "tcp://142.128.4.1:1883"
    private val clientId = "AndroidClient123753"
    private val topics = listOf("stepper/cmd","odom/linear", "odom/angular")
    var appStatus = AppStatus()
    var linearX : String by mutableStateOf("-")
    var angularZ : String by mutableStateOf("-")
    var nGrooves : String by mutableStateOf("-")
    var stepperPitch : String by mutableStateOf("-")
    var log : String by mutableStateOf("Function Not yet Implemented")


    private lateinit var manager: MqttManager

    override fun onCreate(savedInstanceState: Bundle?) {

        manager = MqttManager(applicationContext, serverUri, clientId, topics, this)
        super.onCreate(savedInstanceState)
        setContent {
            ARCControllerTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SetScreen(linearX,angularZ,nGrooves,stepperPitch,log,pubFun = ::updateStatus)
                }

            }
        }
    }
    override fun onMessageReceived(topic : String, message: String) {

        log = message
        if (message.isNotEmpty()) {
            log = log + "\n" + message
        }
        if (topic == "odom/linear"){

            linearX = String.format("%.3f",message.toFloat())

        }
        else if (topic == "odom/angular"){

            angularZ = String.format("%.3f",message.toFloat())
        }
        else if (topic == "can_vel/primitive2"){
            nGrooves = message
        }
        else if (topic == "cmd/vel4"){
            stepperPitch = message
        }

        Log.d(topic,message)
    }
    private fun updateStatus(topic: String, message: String){

        if (topic == "fahren"){

            appStatus.fahren = message.toFloat()

        }
        else if (topic == "fahren_max"){

            appStatus.fahren_max = message.toFloat()

        }
        else if (topic == "lenken"){

            appStatus.lenken = message.toFloat()

        }
        else if (topic == "lenken_max"){

            appStatus.lenken_max = message.toFloat()

        }
        else if (topic == "stepper"){

            appStatus.stepper = message.toFloat()

        }
        else if (topic == "honk"){

            appStatus.honk = message.toBoolean()

        }
        else if (topic == "light"){

            appStatus.light = message.toBoolean()

        }
        else if (topic == "front_lifting"){

            appStatus.front_axle = message

        }
        else if (topic == "rear_lifting"){

            appStatus.rear_axle = message

        }

        val jsonString = Json.encodeToString(appStatus)
        sendMessage("app/status", jsonString)

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