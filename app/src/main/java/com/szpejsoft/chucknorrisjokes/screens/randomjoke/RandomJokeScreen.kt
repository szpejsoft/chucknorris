package com.szpejsoft.chucknorrisjokes.screens.randomjoke

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
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
import coil3.compose.AsyncImage
import com.szpejsoft.chucknorrisjokes.R
import com.szpejsoft.chucknorrisjokes.joke.Joke
import com.szpejsoft.chucknorrisjokes.screens.composables.AlertDialogWithButton
import com.szpejsoft.chucknorrisjokes.screens.composables.ShowInitialState
import com.szpejsoft.chucknorrisjokes.screens.randomjoke.RandomJokeViewModel.RandomJokeResult
import kotlinx.coroutines.launch

@Composable
fun RandomJokeScreen(
    viewModel: RandomJokeViewModel = hiltViewModel(),
) {
    val scope = rememberCoroutineScope()
    val randomJokeResult = viewModel.randomJokeFlow.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.fetchRandomJoke()
        viewModel.setScreenTitle(R.string.app_name)
    }

    when (randomJokeResult) {
        is RandomJokeResult.Success -> ShowJoke(randomJokeResult.joke) { scope.launch { viewModel.fetchRandomJoke() } }
        is RandomJokeResult.Error -> AlertDialogWithButton(
            message = "No joke for you :(. Try again later.",
            buttonText = "Ok"
        ) { scope.launch { viewModel.fetchRandomJoke() } }

        RandomJokeResult.None -> ShowInitialState()
    }
}

@Composable
fun ShowJoke(
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
        Spacer(modifier = Modifier.weight(1f))
        AsyncImage(
            modifier = Modifier
                .width(128.dp)
                .height(128.dp),
            model = joke.iconUrl,
            contentDescription = null,
        )
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = joke.value,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = onNextJokeClicked,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Next Joke")
        }
    }
}
