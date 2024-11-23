package com.szpejsoft.chucknorrisjokes.screens.jokebycategory

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
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
import com.szpejsoft.chucknorrisjokes.joke.Joke
import com.szpejsoft.chucknorrisjokes.screens.jokebycategory.JokeByCategoryViewModel.JokeByCategoryResult.Error
import com.szpejsoft.chucknorrisjokes.screens.jokebycategory.JokeByCategoryViewModel.JokeByCategoryResult.None
import com.szpejsoft.chucknorrisjokes.screens.jokebycategory.JokeByCategoryViewModel.JokeByCategoryResult.Success
import kotlinx.coroutines.launch

@Composable
fun JokeByCategoryScreen(
    category: String,
    viewModel: JokeByCategoryViewModel = hiltViewModel()

) {
    val scope = rememberCoroutineScope()
    val jokeByCategoryResult = viewModel.jokeByCategoryFlow.collectAsState().value

    LaunchedEffect(Unit) {
        scope.launch { viewModel.fetchJokeByCategory(category) }
    }
    Text(text = "Category: $category")
    when (jokeByCategoryResult) {
        Error -> ShowError { scope.launch { viewModel.fetchJokeByCategory(category) } }
        None -> ShowInitialState()
        is Success -> ShowJoke(jokeByCategoryResult.joke) { scope.launch { viewModel.fetchJokeByCategory("animal") } }
    }

}

//TODO refactor - move below composables with ones from RandomJokeScreen to separate file?
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
