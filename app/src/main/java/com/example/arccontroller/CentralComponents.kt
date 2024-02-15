package com.example.arccontroller

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
fun pubData(newa: String,nv: String)
{
    if (!(newa == nv)){
        Log.d(newa,nv)
        //lightFlag = !lightFlag
        //uptStatus("light", lightFlag.toString())
    }
}
@Composable
fun CentralComponents(log : String,
                      uptStatus: (String, String) -> Unit)
{

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(horizontal = 5.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            LidarBtn(funcName = "STEPPER",
                onClick = uptStatus)
            DropDownSliderBtn(funcName = "FAHREN",
                topic = "fahren_max",
                initialValue = 0f,
                minValue = 0f,
                maxValue = 20f,
                onClick = uptStatus)
            DropDownSliderBtn(funcName = "LENKEN",
                topic = "lenken_max",
                initialValue = 0f,
                minValue = 0f,
                maxValue = 20f,
                onClick = uptStatus)
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(1.dp)
                //.border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(8.dp))
                //.fillMaxWidth()
        ) {
            CustomButton(
                modifier = Modifier.padding(10.dp),
                text = "HONK",
                topic = "honk",
                message = "false",
                messageOnPress = "true",
                onClick = uptStatus
            )
            CustomButton(
                modifier = Modifier.padding(10.dp),
                text = "LIGHT",
                topic = "light",
                onClick = uptStatus
            )
        }
        ScroableTextField(
            label = "Log",
            text = log,
            modifier = Modifier
                .width(375.dp)
                .fillMaxHeight()
                .padding(2.dp)
        )

    }
}

