package com.example.arccontroller

import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ScroableTextField(
    label: String,
    text: String,
    modifier: Modifier = Modifier,
    ){
    val scrollState = rememberScrollState(initial = Float.POSITIVE_INFINITY.toInt())

    LaunchedEffect(text) {

        if (scrollState.value == scrollState.maxValue){

            scrollState.scrollTo(scrollState.maxValue)
        }
    }
    //scrollState.scrollTo(scrollState.maxValue)
    OutlinedTextField(
        value = text,
        onValueChange = {},
        label = { Text(
            text = label,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )
        },
        textStyle = LocalTextStyle.current.copy(
            fontSize = 20.sp, // Customize the font size as needed
            fontWeight = FontWeight.Bold, // Customize the font weight as needed
            textAlign = TextAlign.Start
        ),
        singleLine = false,
        maxLines = Int.MAX_VALUE,
        readOnly = true,
        modifier = modifier
            .widthIn(max = 100.dp) // Adjust the max width as needed
            .verticalScroll(scrollState)

        //.padding(vertical = 0.dp)
    )
}