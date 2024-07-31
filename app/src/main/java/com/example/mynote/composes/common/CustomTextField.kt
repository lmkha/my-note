package com.example.mynote.composes.common

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun CustomTextField(
    label: String,
    value: String,
    focusManager: FocusManager,
    isCapitalize: Boolean = false,
    onValueChange: (String) -> Unit,
    roundRadius: Int = 8,
    isPassword: Boolean = false
) {
    TextField(
        label = { Text(label) },
        value = value,
        onValueChange = { onValueChange(it) },
        modifier = Modifier.padding(
            bottom = 8.dp
        ),
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            focusedLabelColor = Color.Black,
            focusedIndicatorColor = Color.Transparent,
            unfocusedTextColor = Color.Black,
            unfocusedLabelColor = Color.Gray,
            unfocusedContainerColor = Color.LightGray,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        keyboardOptions = KeyboardOptions(
            capitalization = if (isCapitalize) KeyboardCapitalization.Sentences else KeyboardCapitalization.None,
            keyboardType = if (isPassword) KeyboardType.Password else KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                val result = focusManager.moveFocus(FocusDirection.Down)
                if (!result) {
                    focusManager.clearFocus()
                }
            }
        ),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        shape = RoundedCornerShape(roundRadius.dp),
    )
}
