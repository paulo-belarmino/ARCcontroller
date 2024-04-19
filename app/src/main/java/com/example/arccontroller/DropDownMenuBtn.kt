package com.example.arccontroller

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp


@Composable
fun DropDownMenuBtn(
    funcName: String,
    dropdownItems: List<String>,
    modifier: Modifier = Modifier,
    onItemClick: (String, String) -> Unit
) {
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
            .padding(16.dp)
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
                .background(Color.Blue)
                .padding(8.dp)
        ) {
            Text(text = funcName,
                 color = Color.White,
                 modifier = Modifier
                    .background(Color.Blue)
                    .padding(8.dp)
            )
        }
        DropdownMenu(
            expanded = isContextMenuVisible,
            onDismissRequest = {
                isContextMenuVisible = false
            },
            offset = pressOffset.copy(
                y = pressOffset.y - itemHeight
            )
        ) {
            dropdownItems.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        onItemClick("lidar",selectionOption)
                        isContextMenuVisible = false
                    })
            }
        }
    }
}