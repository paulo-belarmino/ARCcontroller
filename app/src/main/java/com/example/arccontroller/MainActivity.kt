package com.example.arccontroller

import android.content.Intent
import android.os.Bundle
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

    val format = Json { encodeDefaults = true }
    private val serverUri = "tcp://142.128.4.1:1883"
    private val clientId = "AndroidClient123753"
    private val topics = listOf("stepper/front/pitch","odom/linear", "odom/angular","odom/x","odom/y","odom/yaw")
    var appStatus = AppStatus()
    var linearX : String by mutableStateOf("-")
    var angularZ : String by mutableStateOf("-")
    var pos_x : String by mutableStateOf("-")
    var pos_y : String by mutableStateOf("-")
    var ori_yaw : String by mutableStateOf("-")
    var nGrooves : String by mutableStateOf("-")
    var stepperPitch : String by mutableStateOf("-")
    var log : String by mutableStateOf("Function Not yet Implemented")


    lateinit var manager: MqttManager

    override fun onCreate(savedInstanceState: Bundle?) {

        manager = MqttManager(applicationContext, serverUri, clientId, topics, this)
        super.onCreate(savedInstanceState)
        setContent {
            ARCControllerTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SetScreen(linearX,angularZ,nGrooves,stepperPitch,pos_x,pos_y,ori_yaw,log,
                        uptStatus = ::updateStatus, pubFun = ::sendMessage)
                }

            }
        }
    }
    override fun onMessageReceived(topic : String, message: String) {

        //log = message
        //Log.d(topic, message)
        //if (message.isNotEmpty()) {
          //  log = log + "\n" + message
        //}
        if (topic == "odom/linear"){
            linearX = String.format("%.3f",message.toFloat())
        }
        else if (topic == "odom/angular"){
            angularZ = String.format("%.3f",message.toFloat())
        }
        else if (topic == "odom/x"){
            pos_x = String.format("%.3f",message.toFloat())
        }
        else if (topic == "odom/y"){
            pos_y = String.format("%.3f",message.toFloat())
        }
        else if (topic == "odom/yaw"){
            ori_yaw = String.format("%.3f",message.toFloat())
        }
        else if (topic == "gd_detection/n_grooves"){
            nGrooves = message
        }
        else if (topic == "stepper/front/pitch"){
            stepperPitch = message
        }
        else if (topic == "app/reset"){

            //restartApplication()

        }

        //Log.d(topic,message)
    }

    private fun updateStatus(topic: String, message: String){
        //Log.d(topic, message)
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

            appStatus.stepper = message

        }
        else if (topic == "honk"){

            appStatus.honk = message.toBoolean()

        }
        else if (topic == "light"){

            appStatus.light = !appStatus.light

        }
        else if (topic == "front_axle"){

            appStatus.front_axle = message

        }
        else if (topic == "rear_axle"){

            appStatus.rear_axle = message

        }

        val jsonString = format.encodeToString(appStatus)
        sendMessage("app/status", jsonString)

    }
    private fun sendMessage(topic: String, message: String) {

        if (manager.client.isConnected) {
            manager.publish(topic,message)
        }
    }

    fun restartApplication() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ARCControllerTheme {
        //MainGui()
    }
}