package com.example.arccontroller

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.compose.ui.unit.toSize
import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.math.sqrt

class SetInt{

}
@Composable
fun Lever(
    topic: String,
    height: Dp = 200.dp,
    width: Dp = 50.dp,
    verticalFlag: Boolean = true,
    moved: (y: Float) -> Unit = {  _, -> },
    pubFun: (String, String) -> Unit,
    modifier: Modifier = Modifier,

) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = topic)
        Box(
            modifier = modifier
                .border(width = 3.dp, color = Color.LightGray)
                .width(width)
                .height(height)
            //.border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(8.dp))

        ) {

            var maxAmplitude = 0f
            var dotRadius = 0f
            if (verticalFlag){

                maxAmplitude = with(LocalDensity.current) { (height/2).toPx() }
                dotRadius = with(LocalDensity.current) { (width/2).toPx() }

            }
            else
            {

                maxAmplitude = with(LocalDensity.current) { (width/2).toPx() }
                dotRadius = with(LocalDensity.current) { (height/2).toPx() }

            }

            val centerX = 0f
            val centerY = 0f

            var offsetX by remember { mutableStateOf(centerX) }
            var offsetY by remember { mutableStateOf(centerY) }

            var positionX by remember { mutableStateOf(0f) }
            var positionY by remember { mutableStateOf(0f) }

            fun onChangePos(y: Float){

                //val message : String = String.format("""{"fahren":  %.2f, "lenken":  %.2f}""",y)
                moved(y)
                pubFun(topic,y.toString())
                //Log.d("debug",String.format("%.2f,%.2f,%.2f,%.2f",y,x,radius,theta))
            }

            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .offset {
                        IntOffset(
                            (positionX).roundToInt(),
                            (positionY).roundToInt()
                        )
                    }
                    .pointerInput(Unit) {
                        detectDragGestures(onDragEnd = {
                            offsetX = centerX
                            offsetY = centerY
                            positionX = 0f
                            positionY = 0f
                        }) { pointerInputChange: PointerInputChange, offset: Offset ->
                            var x = offsetX + offset.x - centerX
                            var y = offsetY + offset.y - centerY
                            //Log.d("debug",String.format("%.2f,%.2f,%.2f",y,x,maxAmplitude))
                            pointerInputChange.consume()

                            offsetX += offset.x
                            offsetY += offset.y

                            if (verticalFlag) {

                                x = 0f
                                if (y > maxAmplitude) {

                                    y = maxAmplitude
                                } else if (y < -maxAmplitude) {

                                    y = -maxAmplitude
                                } else {
                                }

                            } else {

                                y = 0f
                                if (x > maxAmplitude) {

                                    x = maxAmplitude
                                } else if (x < -maxAmplitude) {

                                    x = -maxAmplitude
                                } else {
                                }

                            }.apply {
                                positionX = x
                                positionY = y
                            }
                        }
                    }
                    .onGloballyPositioned { coordinates ->
                        onChangePos(
                            if (verticalFlag) -(coordinates.positionInParent().y) / maxAmplitude
                            else coordinates.positionInParent().x / maxAmplitude
                        )
                    }
            ) {
                drawCircle(Color.LightGray,  radius = dotRadius)
            }

        }
    }
}
private fun polarToCartesian(radius: Float, theta: Float): Pair<Float, Float> =
    Pair(radius * cos(theta), radius * sin(theta))
