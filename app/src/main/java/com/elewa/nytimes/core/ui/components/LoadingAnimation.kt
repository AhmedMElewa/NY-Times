package com.elewa.nytimes.core.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun LoadingAnimation(
    modifier: Modifier = Modifier,
    circleSize: Dp = 25.dp,
    circleColor: Color = MaterialTheme.colorScheme.primary,
    spaceBetween: Dp = 10.dp,
    travelDistance: Dp = 20.dp
) {
    val circles = listOf(
        remember { Animatable(initialValue = 0f)},
        remember { Animatable(initialValue = 0f)},
        remember { Animatable(initialValue = 0f)}
    )

    circles.forEachIndexed{ index, animatable ->
        LaunchedEffect(key1 = animatable){
            delay(index * 100L)
            animatable.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = 1200
                        //Animando
                        0.0f at 0 with LinearOutSlowInEasing
                        1.0f at 300 with LinearOutSlowInEasing

                        //tempo em repouso
                        0.0f at 600 with LinearOutSlowInEasing
                        0.0f at 1200 with LinearOutSlowInEasing
                    },
                    repeatMode = RepeatMode.Restart
                )
            )
        }
    }

    val circleValues = circles.map{it.value}
    //para poder usar a funcao de extensao toPx()
    val distance = with(LocalDensity.current){ travelDistance.toPx() }
    val lastCircle = circleValues.size -1

    Row(modifier = modifier){
        circleValues.forEachIndexed { index, value ->
            Box(
               modifier = Modifier
                   .size(circleSize)
                   .graphicsLayer {
                       println("GLAYER- value  ${-value} distance  $distance   = ${-value * distance}")
                       translationY = -value * distance
                   }
                   .background(
                       color = circleColor,
                       shape = RoundedCornerShape(50)

                   )
            )
            if(index != lastCircle){
                Spacer(modifier = Modifier.width(spaceBetween))
            }
        }
    }
}