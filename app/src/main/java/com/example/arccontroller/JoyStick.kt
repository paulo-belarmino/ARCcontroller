package com.example.arccontroller

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.math.sqrt

class SetInt{

}
@Composable
fun JoyStick(
    size: Dp = 200.dp,
    dotSize: Dp = 50.dp,
    moved: (x: Float, y: Float) -> Unit = {  _, _ -> },
    pubFun: (String, String) -> Unit,
    modifier: Modifier = Modifier,

) {
    Box(

        modifier = modifier
            .size(size)
        //.border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(8.dp))

    ) {
        val maxRadius = with(LocalDensity.current) { (size / 2).toPx() }
        val dotRadius = with(LocalDensity.current) { (dotSize / 2).toPx() }
        val centerX = 0f
        val centerY = 0f

        var offsetX by remember { mutableStateOf(centerX) }
        var offsetY by remember { mutableStateOf(centerY) }

        var radius by remember { mutableStateOf(0f) }
        var theta by remember { mutableStateOf(0f) }

        var positionX by remember { mutableStateOf(0f) }
        var positionY by remember { mutableStateOf(0f) }

        fun onChangePos(x: Float,y: Float){

            val message : String = String.format("""{"fahren":  %.2f, "lenken":  %.2f}""",y,x)
            moved(x,y)
            pubFun("can_vel/primitive",message)
        }
        Canvas(
            modifier = Modifier
                .fillMaxSize(),

            ) {
            drawCircle(Color.LightGray, radius = maxRadius, style = Stroke(width = 8.dp.toPx()))
            drawLine(
                start = Offset(x = (maxRadius * 0.293).toFloat(), y = (maxRadius * 0.293).toFloat()),
                end = Offset(x = (1.707*maxRadius).toFloat(), y = (1.707*maxRadius).toFloat()),
                color = Color.LightGray,
                strokeWidth = 5.dp.toPx() // instead of 5.dp.toPx() , you can also pass 5f
            )
            drawLine(
                start = Offset(x = (maxRadius * 0.293).toFloat(), y = (maxRadius * 1.707).toFloat()),
                end = Offset(x = (1.707*maxRadius).toFloat(), y = (0.293*maxRadius).toFloat()),
                color = Color.LightGray,
                strokeWidth = 5.dp.toPx() // instead of 5.dp.toPx() , you can also pass 5f
            )
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
                        radius = 0f
                        theta = 0f
                        positionX = 0f
                        positionY = 0f
                    }) { pointerInputChange: PointerInputChange, offset: Offset ->
                        val x = offsetX + offset.x - centerX
                        val y = offsetY + offset.y - centerY
                        print(x)
                        pointerInputChange.consume()

                        theta = if (x >= 0 && y >= 0) {
                            atan(y / x)
                        } else if (x < 0 && y >= 0) {
                            (Math.PI).toFloat() + atan(y / x)
                        } else if (x < 0 && y < 0) {
                            -(Math.PI).toFloat() + atan(y / x)
                        } else {
                            atan(y / x)
                        }

                        radius = sqrt((x.pow(2)) + (y.pow(2)))

                        offsetX += offset.x
                        offsetY += offset.y

                        if (radius > maxRadius) {
                            polarToCartesian(maxRadius, theta)
                        } else {
                            polarToCartesian(radius, theta)
                        }.apply {
                            positionX = first
                            positionY = second
                        }
                    }
                }
                .onGloballyPositioned { coordinates ->
                    onChangePos(
                        (coordinates.positionInParent().x) / maxRadius,
                        -(coordinates.positionInParent().y) / maxRadius
                    )
                }
        ) {
            drawCircle(Color.LightGray,  radius = dotRadius)
        }

    }

}
private fun polarToCartesian(radius: Float, theta: Float): Pair<Float, Float> =
    Pair(radius * cos(theta), radius * sin(theta))
