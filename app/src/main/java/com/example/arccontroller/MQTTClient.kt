package com.example.arccontroller

import android.content.Context
import android.util.Log
import info.mqtt.android.service.Ack
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import info.mqtt.android.service.MqttAndroidClient;
//import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttException
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence


class MqttManager(
    private val context: Context,
    private val serverUri: String,
    private val clientId: String,
    private val sub_topics: List<String>,
    private val callback: MessageCallback // generate by the interface in MessageCallback file
) {
    private val persistence = MemoryPersistence()
    // val client = MqttAndroidClient(context, serverUri, clientId, persistence)
    val client = MqttAndroidClient(context, serverUri, clientId, Ack.AUTO_ACK)

    init {

        connect()
    }
    fun connect() {
        val options = MqttConnectOptions()
        options.isAutomaticReconnect = true
        //options.setCleanSession(false)

        client.setCallback(object : MqttCallbackExtended {
            override fun connectComplete(reconnect: Boolean, serverURI: String?) {
                if (reconnect) {
                    // Reconnection logic if needed
                } else {
                    // Initial connection logic if needed
                }
            }

            override fun connectionLost(cause: Throwable?) {
                // Connection lost logic if needed
            }

            override fun messageArrived(topic: String?, message: MqttMessage?) {
                // Handle incoming messages
                message?.let {
                    val incomingMessage = String(it.payload)
                    if (topic != null) {
                        callback.onMessageReceived(topic,incomingMessage)
                    }
                }
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {
                // Delivery complete logic if needed
            }
        })

        try {

            client.connect(options, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.d("error","Connection Success")
                    subscribeToTopic()

                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.d("error","Connection Failed")
                }
            })
        }
        catch (ex: MqttException) {
            // Handle exception
        }

    }

    private fun subscribeToTopic() {
        try {
            for (topic in sub_topics) {
                client.subscribe(topic, 0, null, object : IMqttActionListener {
                    override fun onSuccess(asyncActionToken: IMqttToken?) {
                        Log.d("TAG", "Subscribed to topic, $topic")
                    }

                    override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                        Log.d("TAG", "Subscription to topic $topic failed!")
                    }
                })
            }
        } catch (ex: MqttException) {
            // Handle exception
        }
    }

    fun publish(topic: String, message: String) {
        try {
            val mqttMessage = MqttMessage()

            mqttMessage.payload = message.toByteArray()
            //Log.d(topic,message)
            client.publish(topic, mqttMessage, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    // Message published success logic if needed
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    // Message published failure logic if needed
                }
            })
        } catch (ex: MqttException) {
            // Handle exception
        }
    }
}



