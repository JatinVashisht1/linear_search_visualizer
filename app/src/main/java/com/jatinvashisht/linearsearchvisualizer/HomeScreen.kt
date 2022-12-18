package com.jatinvashisht.linearsearchvisualizer

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.delay

fun linearSearch(list: List<Int>, target: Int): Int{
    for(i in list.indices){
        val element = list[i]
        if(element == target) return i;
    }
    return -1;
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    currentOnStart: () -> Unit,
    currentOnStop: () -> Unit,

) {
    var xState by remember { mutableStateOf(0) }

    val intList by remember{ mutableStateOf(listOf(5, 4, 10, 50, 30)) }
    val target by remember{ mutableStateOf(50) }
    DisposableEffect(lifecycleOwner){
        val observer = LifecycleEventObserver{_, event ->
            if(event == Lifecycle.Event.ON_START){
                currentOnStart()
            }else if(event == Lifecycle.Event.ON_STOP){
                currentOnStop()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    var start by remember{ mutableStateOf(false) }
    var currInt by remember{ mutableStateOf(-1)}
    LaunchedEffect(key1 = start){
        if(!start){
            return@LaunchedEffect
        }
        for (i in intList.indices) {
            val curr = intList[i]
            currInt = curr
            if (currInt == target) {
                return@LaunchedEffect
            }
            delay(1000L)
        }
    }
    BoxWithConstraints(
        modifier = modifier
    ) {
        val width by remember{ mutableStateOf(this.maxWidth)}
        val height by remember{ mutableStateOf(this.maxHeight)}

        Column(modifier = Modifier.fillMaxSize()) {
            Button(
                onClick = { start = !start },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = if(start)"Stop" else "Start")
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Boxes(intList = intList, currInt = currInt, target = target)
            }
        }
    }
}

@Composable
fun Boxes(intList: List<Int>, currInt: Int, target: Int) {
    intList.forEach {
        var colorState by remember{ mutableStateOf(Color.DarkGray) }
        val color = animateColorAsState(targetValue = colorState)
        if(currInt == it){
            colorState = Color(0, 200, 83, 255)
            if(currInt != target){
                colorState = Color.Gray
            }
        }
//        if(currInt != target){
//            colorState = Color.LightGray
//        }
        Box(
            modifier = Modifier
                .size(75.dp)
                .drawBehind {
                    drawRect(color = color.value)
                },
            contentAlignment = Alignment.Center,
        ) {
            Text(text = it.toString(), fontSize = MaterialTheme.typography.h6.fontSize)
        }
    }
}

@Composable
fun ShootArrow(
    modifier: Modifier,
    xOffset: Int,
    item: @Composable () -> Unit,
) {
    // Outer box
    Box(modifier){
        Box(
            modifier = Modifier
                .offset(x = xOffset.dp)
                .align(Alignment.CenterStart)
        ){
            item()
        }

    }
}

@Composable
fun Arrow() {
    Image(
        imageVector = Icons.Default.KeyboardArrowUp,
        contentDescription = null,
        modifier = Modifier
            .size(75.dp),
        colorFilter = ColorFilter.tint(Color.Cyan)
    )
}

