package com.example.arccontroller

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CentralComponents(log : String,
                      pubFun: (String, String) -> Unit)
{
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(horizontal = 1.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            DropDownSliderBtn(funcName = "STEPPER",
                topic = "stepper",
                initialValue = -60f,
                minValue = -60f,
                maxValue = 15f,
                onClick = pubFun)
            DropDownSliderBtn(funcName = "FAHREN",
                topic = "fahren_max",
                initialValue = 0f,
                minValue = 0f,
                maxValue = 20f,
                onClick = pubFun)
            DropDownSliderBtn(funcName = "LENKEN",
                topic = "lenken_max",
                initialValue = 0f,
                minValue = 0f,
                maxValue = 20f,
                onClick = pubFun)
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(1.dp)
                //.border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(8.dp))
                //.fillMaxWidth()
        ) {
            CustomButton(modifier = Modifier.padding(10.dp),
                text = "HONK",
                topic = "horn",
                message = "False",
                messageOnPress = "True",
                onClick = pubFun
            )
            CustomButton(modifier = Modifier.padding(10.dp),
                text = "LIGHT",
                topic = "light",
                message = "True",
                onClick = pubFun
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

