package com.szpejsoft.chucknorrisjokes.screens.randomjoke

import android.text.Layout
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun RandomJokeScreen(
    viewModel: RandomJokeViewModel = hiltViewModel()
) {
    val randomJokeResult = viewModel.randomJokeFlow.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.fetchRandomJoke()
    }
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (randomJokeResult) {
            is RandomJokeViewModel.RandomJokeResult.Success -> Text(text = randomJokeResult.joke.value)
            is RandomJokeViewModel.RandomJokeResult.Error -> Text(text = randomJokeResult.message)
            RandomJokeViewModel.RandomJokeResult.None -> Unit
        }
    }
}