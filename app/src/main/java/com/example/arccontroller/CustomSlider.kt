package com.example.arccontroller

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import java.lang.reflect.Modifier
import kotlin.math.roundToInt

@Composable
fun CustomSlider(
    minValue: Float,
    maxValue: Float,
    initialValue: Float,
    label: String = "",
    onValueChangeFinished: (Float) -> Unit
) {
    var sliderPosition by remember { mutableStateOf(initialValue) }

    var finalValue  = sliderPosition.toInt().toString()

    Text(text = label + finalValue)
    Slider(value = sliderPosition,
        onValueChange = { newValue -> sliderPosition = newValue },
        onValueChangeFinished = {onValueChangeFinished(sliderPosition)},
        valueRange = minValue..maxValue,
        steps = (maxValue - minValue).toInt() - 1,
    )
}
