package com.example.arccontroller

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class Option {
    OPTION1, OPTION2, OPTION3
}
@Composable
fun LidarBtn(
    funcName: String,
    modifier: Modifier = Modifier,
    minValue: Float = -60f,
    maxValue: Float = 15f,
    onClick: (String, String) -> Unit
) {

    var selectedValue by rememberSaveable { mutableFloatStateOf(-60f) }
    var selectedFeq by rememberSaveable { mutableFloatStateOf(10f) }
    var displayValue by rememberSaveable {mutableStateOf("")}
    var isContextMenuVisible by rememberSaveable {mutableStateOf(false)}
    var pressOffset by remember {mutableStateOf(DpOffset.Zero)}
    var itemHeight by remember {mutableStateOf(0.dp)}

    val interactionSource = remember {
        MutableInteractionSource()
    }
    val density = LocalDensity.current

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        modifier = modifier
            .padding(10.dp)
            .onSizeChanged {
                itemHeight = with(density) { it.height.toDp() }
            }
    ) {
        Box(
            modifier = Modifier
                //.width(150.dp)
                .indication(interactionSource, LocalIndication.current)
                .pointerInput(true) {
                    detectTapGestures(
//                        onLongPress = {
//                            isContextMenuVisible = true
//                            pressOffset = DpOffset(it.x.toDp(), it.y.toDp())
//                        },
                        onPress = {
                            val press = PressInteraction.Press(it)
                            interactionSource.emit(press)
                            tryAwaitRelease()
                            interactionSource.emit(PressInteraction.Release(press))
                            isContextMenuVisible = true
                            pressOffset = DpOffset(it.x.toDp(), it.y.toDp())
                        }
                    )
                }
                .background(MaterialTheme.colorScheme.primary)
                .padding(5.dp)
        ) {
            Text(text =  funcName + ": " + displayValue,
                 color = Color.White,
                 style = TextStyle(fontSize = 13.sp, fontWeight = FontWeight.Bold),
                 modifier = Modifier
                    .padding(5.dp)

            )
        }
        DropdownMenu(
            expanded = isContextMenuVisible,
            onDismissRequest = {
                isContextMenuVisible = false
            },
//            offset = pressOffset.copy(
//                y = pressOffset.y - itemHeight
//            )
        ) {
            fun pubData(goal: String,value: Float)
            {
                if (goal == "FEQ") selectedFeq = value
                else selectedValue = value
                val value_str = String.format("%.2f",value)
                if (goal == "CTM") displayValue = value_str
                else displayValue = goal
                onClick("stepper", goal + value_str)
            }

            Box(
                modifier = Modifier
                    .width(200.dp)
            )
            {
                Column(modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    CustomSlider(
                        minValue = minValue,
                        maxValue = maxValue,
                        initialValue = selectedValue,
                        label = "Target Angle: ",
                        onValueChangeFinished = { value -> pubData(
                            if (value == minValue) "MIN"
                            else if (value == maxValue) "MAX"
                            else "CTM",value) }
                    )
                    Button(
                        modifier = modifier,
                        onClick = { pubData("LOP",0f) },
                        interactionSource = interactionSource,

                        ) {
                        Text(text = "Loop" , fontWeight = FontWeight.Bold)
                    }
                    Button(
                        modifier = modifier,
                        onClick = { pubData("HLD",0f) },
                        interactionSource = interactionSource,

                        ) {
                        Text(text = "Hold" , fontWeight = FontWeight.Bold)
                    }
                    Button(
                        modifier = modifier,
                        onClick = { pubData("CTM",0f) },
                        interactionSource = interactionSource,

                        ) {
                        Text(text = "0" , fontWeight = FontWeight.Bold)

                    }
                    CustomSlider(
                        minValue = 1f,
                        maxValue = 100f,
                        initialValue = selectedFeq,
                        label = "Scan Time: ",
                        onValueChangeFinished = { value -> pubData("FEQ",value) }
                    )
                }
            }
        }
    }
}
