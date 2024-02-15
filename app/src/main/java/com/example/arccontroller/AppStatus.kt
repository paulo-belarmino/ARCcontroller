package com.example.arccontroller;

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class AppStatus(
    var fahren: Float = 0f,
    var fahren_max: Float = 0f,
    var lenken: Float = 0f,
    var lenken_max: Float = 0f,
    var stepper:  String = "HLD0",
    var honk: Boolean = false,
    var light: Boolean = false,
    var front_axle: String = "HOLD",
    var rear_axle: String = "HOLD",
    // Add more properties as needed to represent the state of your UI
)