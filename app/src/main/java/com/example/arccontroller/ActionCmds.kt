package com.example.arccontroller

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ActionCmds(name : String,
            onClick: (String, String) -> Unit)
{

    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(2.dp)
            .border(width = 3.dp, color = Color.Black, shape = RoundedCornerShape(8.dp))
    )
    {
        Text(modifier = Modifier.padding(2.dp),
            text = name,
            fontWeight = FontWeight.Bold
        )
        CustomButton(
            modifier = Modifier.padding(5.dp)
            .fillMaxWidth(),
            text = "START",
            topic = name ,
            message = "START",
            onClick = onClick

        )
        CustomButton(
            modifier = Modifier.padding(5.dp)
            .fillMaxWidth(),
            text = "STOP",
            topic = name,
            message = "STOP",
            onClick = onClick
        )
    }
}
