package com.example.arccontroller

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay


@Composable
fun SetScreen(linearX : String,
              angularZ : String,
              nGrooves : String,
              stepperPitch : String,
              x_pos : String,
              y_pos : String,
              log : String,
              uptStatus: (String, String) -> Unit,
              pubFun: (String, String) -> Unit,
              modifier: Modifier = Modifier) {

    LaunchedEffect(Unit) {

        while (true) {

            pubFun("app/timeout", "running")
            delay(150)

        }

    }
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start
    ) {

        DataBar(linearX, angularZ, nGrooves, stepperPitch, x_pos,y_pos, modifier)

        Row(
            modifier = Modifier,
                //.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Lever(
                topic = "fahren",
                uptStatus = uptStatus,
                modifier = Modifier.padding(10.dp)
            )
            CentralComponents(log,uptStatus)
            RightComponents(uptStatus)
        }
    }

}
@Composable
fun DataBar(dataDisplay1 : String,
            dataDisplay2 : String,
            dataDisplay3 : String,
            dataDisplay4: String,
            dataDisplay5 : String,
            dataDisplay6: String,
            modifier : Modifier = Modifier){
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(2.dp)

    ) {
        OutlinedTextFieldWithLabel(
            text = dataDisplay1,
            label = "Linear X"
        )
        OutlinedTextFieldWithLabel(
            text = dataDisplay2,
            label = "Angular Z"
        )
        OutlinedTextFieldWithLabel(
            text = dataDisplay3,
            label = "N Grooves"
        )
        OutlinedTextFieldWithLabel(
            text = dataDisplay4,
            label = "Lidar Angle"
        )
        OutlinedTextFieldWithLabel(
            text = dataDisplay5,
            label = "X"
        )
        OutlinedTextFieldWithLabel(
            text = dataDisplay6,
            label = "Y"
        )

    }
}


