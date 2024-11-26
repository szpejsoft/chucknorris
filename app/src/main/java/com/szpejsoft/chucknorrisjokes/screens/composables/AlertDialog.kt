package com.szpejsoft.chucknorrisjokes.screens.composables

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun AlertDialogWithButton(
    message: String,
    buttonText: String,
    onOkClicked: () -> Unit
) {
    AlertDialog(
        text = {
            Text(message)
        },
        onDismissRequest = { },
        confirmButton = {
            Button(
                onClick = onOkClicked
            ) {
                Text(buttonText)
            }
        },
    )
}