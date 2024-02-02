package com.example.arccontroller

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun SetScreen(linearX : String,
              angularZ : String,
              nGrooves : String,
              stepperPitch : String,
              log : String,
              pubFun: (String, String) -> Unit,
              modifier: Modifier = Modifier) {


    var fahren by remember { mutableStateOf( 0f) }
    //var message : String by remember { mutableStateOf("") }
    // message = String.format("fahren: %.2f, lenken: %.2f", position[1], position[0])
    //Log.d("debug",message)

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start
    ) {

        DataBar(linearX, angularZ, nGrooves, stepperPitch, modifier)

        Row(
            modifier = Modifier,
                //.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Lever(
                topic = "fahren",
                moved = { y: Float -> fahren = 0f },
                pubFun = pubFun,
                modifier = Modifier.padding(10.dp)
            )
            CentralComponents(log,pubFun)
            LeftComponents(pubFun)
        }
    }

}
@Composable
fun DataBar(dataDisplay1 : String,
            dataDisplay2 : String,
            dataDisplay3 : String,
            dataDisplay4: String,
            modifier : Modifier = Modifier){
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(2.dp)
        //.border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(8.dp))
        //.padding(2.dp)
        //.fillMaxWidth()
        //.fillMaxSize()
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

    }
}


