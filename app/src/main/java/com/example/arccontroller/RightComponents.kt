package com.example.arccontroller

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RightComponents(uptStatus: (String, String) -> Unit)
{

    Column(modifier = Modifier
        //.width(250.dp)
        .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Lever(
            topic = "lenken",
            height = 50.dp,
            width = 250.dp,
            verticalFlag = false,
            uptStatus = uptStatus,
            modifier = Modifier.padding(5.dp)
            //.fillMaxWidth(),
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                //.padding(2.dp)
                //.fillMaxWidth()


        ) {
            //Box( modifier = Modifier.weight(1f)
            Box(modifier = Modifier
                .weight(1f))
            {
                AxleCmd("front", uptStatus)
            }
            Box(modifier = Modifier
                .weight(1f))
            {
                AxleCmd("rear", uptStatus)
            }
        }
    }

}