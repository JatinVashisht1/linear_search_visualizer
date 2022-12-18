package com.jatinvashisht.linearsearchvisualizer

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun ArrayDescriptionInputManager(
    modifier: Modifier = Modifier,
    textFieldHeight: Dp = 0.dp,
    onInputArraySizeTextFieldValueChanged: (String)->Unit,
    wrongSizeEntered: () -> Unit,
) {
    var elements by remember{ mutableStateOf(2) }
    var showElementInputTextField by remember{ mutableStateOf(false) }
    Row(
        modifier = modifier
    ) {
        TextFieldInputArrayDescription(
            modifier = Modifier
                .fillMaxWidth(if (elements <= 0 || elements > 10) 0.5f else (1f / elements.toFloat()))
                .height(textFieldHeight)
                .padding(horizontal = 8.dp),
            value = elements.toString(),
            label = "size",
            placeholder = "Enter array size"
        ){newSize ->
            try{
                val newSizeInt = newSize.toInt()
                if (newSizeInt > 10 || newSizeInt <= 0) {
                    wrongSizeEntered()
                    showElementInputTextField = false
                } else {
                    elements = newSizeInt
                    showElementInputTextField = true
                }
            } catch (exception: NumberFormatException) {
                showElementInputTextField = true
                onInputArraySizeTextFieldValueChanged("enter correct value")
            }
        }

        AnimatedVisibility(visible = showElementInputTextField) {
            var elementInputTextFieldRequired by remember { mutableStateOf(elements.toInt()) }
//            while(elementInputTextFieldRequired-- != 0){
            for(i in 0 until 5){
                TextFieldInputArrayDescription(
                    modifier = Modifier
                        .fillMaxWidth(1f / elements.toFloat())
                        .height(textFieldHeight)
                        .padding(horizontal = 8.dp),
                    value = "5",
                    onInputArraySizeTextFieldValueChanged = onInputArraySizeTextFieldValueChanged
                )
            }
//            }
        }

    }
}

@Composable
fun TextFieldInputArrayDescription(
    modifier: Modifier = Modifier,
    value: String = "",
    placeholder: String="",
    label: String="",
    onInputArraySizeTextFieldValueChanged: (String) -> Unit,

    ) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onInputArraySizeTextFieldValueChanged,
        label = { Text(text = label) },
        placeholder = { Text(text = placeholder) },
    )
}