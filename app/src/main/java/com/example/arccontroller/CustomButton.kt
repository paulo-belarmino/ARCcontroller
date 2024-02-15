package com.example.arccontroller

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    text: String,
    topic: String,
    message: String = "",
    messageOnPress: String = "",
    onClick: (String, String) -> Unit = {  _, _ -> }
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    Button(
        modifier = modifier,
        onClick = { onClick(topic, message) },
        interactionSource = interactionSource,

    ) {
        Text(text = text, fontWeight = FontWeight.Bold)
        if (isPressed && messageOnPress != ""){ onClick(topic, messageOnPress) }
    }
}