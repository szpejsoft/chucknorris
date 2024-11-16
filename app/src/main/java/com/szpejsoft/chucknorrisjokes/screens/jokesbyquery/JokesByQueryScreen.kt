package com.szpejsoft.chucknorrisjokes.screens.jokesbyquery

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.szpejsoft.chucknorrisjokes.joke.Joke
import com.szpejsoft.chucknorrisjokes.screens.jokesbyquery.JokesByQueryViewModel.JokesByQueryResult
import kotlinx.coroutines.launch

@Composable
fun JokesByQueryScreen(
    viewModel: JokesByQueryViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val jokesByQueryResult = viewModel.jokesByQueryFlow.collectAsState().value

    val searchQuery = remember { mutableStateOf("") }

    Scaffold(
        content = { padding ->
            Surface(
                modifier = Modifier
                    .padding(padding)
                    .padding(horizontal = 12.dp)
            ) {
                Column {
                    SearchBar(query = searchQuery.value,
                        onQueryChange = { query -> searchQuery.value = query },
                        onSearch = { scope.launch { viewModel.fetchJokesByQuery(searchQuery.value) } }
                    )

                    when (jokesByQueryResult) {
                        is JokesByQueryResult.Success -> ShowJokes(jokesByQueryResult.jokes)
                        is JokesByQueryResult.Error -> ShowError {
                            scope.launch {
                                viewModel.fetchJokesByQuery(
                                    searchQuery.value
                                )
                            }
                        }

                        JokesByQueryResult.None -> ShowInitialState()
                    }
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
        Text(text = "No jokes for you :(")
    }
}

@Composable
private fun ShowError(
    onOkClicked: () -> Unit
) {
    AlertDialog(
        text = {
            Text("No jokes for you :(. Try again later.")
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
fun ShowJokes(jokes: List<Joke>) {
    LazyColumn {
        items(jokes.size) { index ->
            Box(
                modifier = Modifier.padding(top = 4.dp)
            ) { Text(text = jokes[index].value) }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String = "Search",
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier.fillMaxWidth(),
        placeholder = { Text(text = hint) },
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(Icons.Filled.Clear, contentDescription = null)
                }
            }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { onSearch(query) })
    )
}
