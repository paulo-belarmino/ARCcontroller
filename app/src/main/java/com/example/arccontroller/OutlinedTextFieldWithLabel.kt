package com.example.arccontroller

import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OutlinedTextFieldWithLabel(
    label: String,
    text: String,
    modifier: Modifier = Modifier

) {
    OutlinedTextField(
        value = text,
        onValueChange = { /* No-op, as the composable only displays text */ },
        label = { Text(
            text = label,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )
        },
        textStyle = LocalTextStyle.current.copy(
            fontSize = 20.sp, // Customize the font size as needed
            fontWeight = FontWeight.Bold, // Customize the font weight as needed
            textAlign = TextAlign.End
        ),
        singleLine = true,
        readOnly = true,
        modifier = modifier
            .widthIn(max = 100.dp) // Adjust the max width as needed

        //.padding(vertical = 0.dp)
    )
}