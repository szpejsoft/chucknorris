package com.szpejsoft.chucknorrisjokes.screens.jokesbyquery

import androidx.lifecycle.ViewModel
import com.szpejsoft.chucknorrisjokes.joke.FetchJokesByQueryUseCase
import com.szpejsoft.chucknorrisjokes.joke.FetchJokesByQueryUseCase.FetchJokesByQueryResult
import com.szpejsoft.chucknorrisjokes.joke.Joke
import com.szpejsoft.chucknorrisjokes.screens.jokesbyquery.JokesByQueryViewModel.JokesByQueryResult.Error
import com.szpejsoft.chucknorrisjokes.screens.jokesbyquery.JokesByQueryViewModel.JokesByQueryResult.None
import com.szpejsoft.chucknorrisjokes.screens.jokesbyquery.JokesByQueryViewModel.JokesByQueryResult.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class JokesByQueryViewModel
@Inject constructor(
    private val fetchJokesByQueryUseCase: FetchJokesByQueryUseCase
) : ViewModel() {
    sealed class JokesByQueryResult {
        data object None : JokesByQueryResult()
        data class Success(val jokes: List<Joke>) : JokesByQueryResult()
        data object Error : JokesByQueryResult()
    }

    private val _jokesByQueryFlow = MutableStateFlow<JokesByQueryResult>(None)
    val jokesByQueryFlow: StateFlow<JokesByQueryResult> = _jokesByQueryFlow

    suspend fun fetchJokesByQuery(query: String) {
        withContext(Dispatchers.Main.immediate) {
            val result = when (val jokesResult = fetchJokesByQueryUseCase.fetchJokes(query)) {
                FetchJokesByQueryResult.Error -> Error
                is FetchJokesByQueryResult.Success -> Success(jokesResult.jokes)

            }
            _jokesByQueryFlow.value = result
        }
    }
}
