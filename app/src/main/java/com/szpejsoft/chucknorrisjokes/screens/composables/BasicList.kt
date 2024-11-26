package com.szpejsoft.chucknorrisjokes.screens.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun ShowList(
    elements: List<String>,
    onElementClicked: (String) -> Unit = {}
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 4.dp),
    ) {
        items(elements.size) { index ->
            Box(
                modifier = Modifier
                    .clickable { onElementClicked(elements[index]) }
                    .padding(12.dp)
                    .fillMaxSize()
            ) {
                Text(text = elements[index])
            }
            if (index < elements.size - 1) {
                HorizontalDivider(thickness = 1.dp)
            }
        }
    }
}
