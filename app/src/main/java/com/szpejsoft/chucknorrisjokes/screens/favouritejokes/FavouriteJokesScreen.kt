package com.szpejsoft.chucknorrisjokes.screens.favouritejokes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.szpejsoft.chucknorrisjokes.R
import com.szpejsoft.chucknorrisjokes.joke.FavouriteJoke
import com.szpejsoft.chucknorrisjokes.screens.composables.ShowList

@Composable
fun FavouriteJokesScreen(
    favouriteJokesViewModel: FavouriteJokesViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        favouriteJokesViewModel.setScreenTitle(R.string.screen_favourite_jokes_title)
    }

    val favouriteJokes = favouriteJokesViewModel.observeFavouriteJokes()
        .collectAsState(listOf())
        .value

    if (favouriteJokes.isEmpty()) {
        ShowEmptyState()
    } else {
        ShowFavouriteJokes(favouriteJokes)
    }

}

@Composable
fun ShowFavouriteJokes(favouriteJokes: List<FavouriteJoke>) {
    ShowList(favouriteJokes.map { it.value })
}

@Composable
fun ShowEmptyState() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            textAlign = TextAlign.Center,
            text = stringResource(R.string.message_no_favourite_jokes),
        )
    }
}
