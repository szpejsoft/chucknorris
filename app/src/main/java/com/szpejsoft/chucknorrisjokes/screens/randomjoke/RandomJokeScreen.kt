package com.szpejsoft.chucknorrisjokes.screens.randomjoke

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.szpejsoft.chucknorrisjokes.joke.Joke
import kotlinx.coroutines.launch

@Composable
fun RandomJokeScreen(
    viewModel: RandomJokeViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val randomJokeResult = viewModel.randomJokeFlow.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.fetchRandomJoke()
    }
    Scaffold(
        content = { padding ->
            Surface(
                modifier = Modifier
                    .padding(padding)
                    .padding(horizontal = 12.dp)
            ) {
                when (randomJokeResult) {
                    is RandomJokeViewModel.RandomJokeResult.Success ->
                        ShowJoke(padding, randomJokeResult.joke) { scope.launch { viewModel.fetchRandomJoke() } }

                    is RandomJokeViewModel.RandomJokeResult.Error -> ShowError { scope.launch { viewModel.fetchRandomJoke() } }

                    RandomJokeViewModel.RandomJokeResult.None -> ShowInitialState()
                }
            }
        }
    )
}

@Composable
private fun ShowInitialState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Loading...")
    }
}

@Composable
private fun ShowError(
    onOkClicked: () -> Unit
) {
    AlertDialog(
        text = {
            Text("No joke for you :(. Try again later.")
        },
        onDismissRequest = { },
        confirmButton = {
            Button(
                onClick = onOkClicked
            ) {
                Text("OK")
            }
        },
    )
}


@Composable
fun ShowJoke(
    padding: PaddingValues,
    joke: Joke,
    onNextJokeClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = joke.value,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Button(
            onClick = onNextJokeClicked,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Next Joke")
        }
    }
}
