package com.example.arccontroller

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun SetScreen(linearX : String,
              angularZ : String,
              nGrooves : String,
              stepperPitch : String,
              pubFun: (String, String) -> Unit,
              modifier: Modifier = Modifier) {

    var position by remember { mutableStateOf(listOf(0f, 0f)) }
    var message : String by remember { mutableStateOf("") }
    message = String.format("fahren: %.2f, lenken: %.2f", position[1], position[0])

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DataBar(linearX, angularZ,nGrooves, stepperPitch,modifier)
//            Text(
//                text = message
//            )
            JoyStick(
                //moved = {x: Float, y: Float ->  pubFun(joyTopic,(x+y).toString())}, //position = listOf(x, y)
                moved = {x: Float, y: Float ->  position = listOf(x, y)},
                pubFun = pubFun,
                modifier = Modifier.padding(25.dp)
            )
        }

        ExtraCmds(pubFun)
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
@Composable
fun ExtraCmds(pubFun: (String, String) -> Unit,
              modifier: Modifier = Modifier) {

    Column(modifier = Modifier.width(350.dp)) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            CustomButton(modifier = Modifier.width(100.dp),
                text = "Max",
                topic = "stepper/cmd",
                message = "MAX",
                onClick = pubFun
            )
            CustomButton(modifier = Modifier.width(100.dp),
                text = "Min",
                topic = "stepper/cmd",
                message = "MIN",
                onClick = pubFun
            )
            CustomButton(modifier = Modifier.width(100.dp),
                text = "Loop",
                topic = "stepper/cmd",
                message = "LOOP",
                onClick = pubFun
            )
        }
        MySliderDemo("Fahren","max_fahren",pubFun)
        MySliderDemo("Lenken","max_lenken",pubFun)
        AxleCmd(pubFun)
    }
}
@Composable
fun AxleCmd(pubFun: (String, String) -> Unit,
            modifier: Modifier = Modifier
) {

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(2.dp)
            //.border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(8.dp))
            .padding(2.dp)
    ) {

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(2.dp)
                .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(8.dp))
                .padding(2.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Front Axle:",
                fontWeight = FontWeight.Bold
            )
            CustomButton(modifier = Modifier.width(100.dp),
                text = "UP",
                topic = "front_axle/cmd",
                message = "LIFTING",
                messageOnPress = "HOLD",
                onClick = pubFun

            )
            CustomButton(modifier = Modifier.width(100.dp),
                text = "DOWN",
                topic = "front_axle/cmd",
                message = "LOWERING",
                messageOnPress = "HOLD",
                onClick = pubFun
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(2.dp)
                .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(8.dp))
                .padding(2.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Rear Axle:",
                fontWeight = FontWeight.Bold
            )
            CustomButton(modifier = Modifier.width(100.dp),
                text = "UP",
                topic = "rear_axle/cmd",
                message = "LIFTING",
                messageOnPress = "HOLD",
                onClick = pubFun
            )
            CustomButton(modifier = Modifier.width(100.dp),
                text = "DOWN",
                topic = "rear_axle/cmd",
                message = "LOWERING",
                messageOnPress = "HOLD",
                onClick = pubFun
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .padding(2.dp)
                //.border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(8.dp))
                .padding(2.dp)
                .fillMaxWidth()
        ) {
            CustomButton(modifier = Modifier.width(100.dp),
                text = "HONK",
                topic = "set_horn",
                message = "False",
                messageOnPress = "True",
                onClick = pubFun
            )
            CustomButton(modifier = Modifier.width(100.dp),
                text = "LIGHT",
                topic = "set_light",
                message = "True",
                onClick = pubFun
            )
        }
    }
}

@Composable
fun MySliderDemo(name: String,
                 topic: String,
                 pubFun: (String, String) -> Unit) {
    var sliderPosition by remember { mutableStateOf(0f)}

    var maxValue  = (50 * sliderPosition).toInt().toString()
    Text(text = name + ": " + maxValue)
    Slider(value = sliderPosition,
        onValueChange = { sliderPosition = it },
        onValueChangeFinished = {pubFun(topic,maxValue)})
}

@Composable
fun CustomButton(
    modifier : Modifier = Modifier,
    text: String,
    topic: String,
    message: String = "",
    messageOnPress : String = "",
    onClick: (String, String) -> Unit = {  _, _ -> }
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    Button(
        modifier = modifier,
        onClick = { onClick(topic, message) },
        interactionSource = interactionSource
    ) {
        Text(text = text, fontWeight = FontWeight.Bold)
        if (isPressed && messageOnPress != ""){ onClick(topic, messageOnPress) }
    }
}

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
        )},
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