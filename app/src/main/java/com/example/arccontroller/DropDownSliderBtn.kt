package com.example.arccontroller

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp


@Composable
fun DropDownSliderBtn(
    funcName: String,
    topic: String,
    modifier: Modifier = Modifier,
    initialValue: Float = 0f,
    minValue: Float = 0f,
    maxValue: Float = 10f,
    onClick: (String, String) -> Unit
) {

    var selectedValue by rememberSaveable {mutableStateOf(initialValue)}
    var isContextMenuVisible by rememberSaveable {mutableStateOf(false)}
    var pressOffset by remember {mutableStateOf(DpOffset.Zero)}
    var itemHeight by remember {mutableStateOf(0.dp)    }
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
            Text(text =  String.format(funcName + ":  %.2f", selectedValue),
                 color = Color.White,
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
            fun pubData(value: Float)
            {
                selectedValue = value
                onClick(topic,value.toString())
            }
            Box(
                modifier = Modifier
                    .width(200.dp)
            )
            {

                CustomSlider(
                    minValue = minValue,
                    maxValue = maxValue,
                    initialValue = selectedValue,
                    onValueChangeFinished = {value -> pubData(value)}
                )
            }

        }
    }
}
