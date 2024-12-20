package com.szpejsoft.chucknorrisjokes.screens.jokesbyquery

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import com.szpejsoft.chucknorrisjokes.common.screentitle.SetScreenTitleUseCase
import com.szpejsoft.chucknorrisjokes.joke.usecases.FetchJokesByQueryUseCase
import com.szpejsoft.chucknorrisjokes.joke.usecases.FetchJokesByQueryUseCase.FetchJokesByQueryResult
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
@Inject
constructor(
    private val fetchJokesByQueryUseCase: FetchJokesByQueryUseCase,
    private val setScreenTitleUseCase: SetScreenTitleUseCase
) : ViewModel() {
    sealed class JokesByQueryResult {
        data object None : JokesByQueryResult()
        data class Success(val jokes: List<Joke>) : JokesByQueryResult()
        data object Error : JokesByQueryResult()
    }

    val jokesByQueryFlow: StateFlow<JokesByQueryResult> get() = _jokesByQueryFlow
    private val _jokesByQueryFlow = MutableStateFlow<JokesByQueryResult>(None)

    suspend fun fetchJokesByQuery(query: String) {
        withContext(Dispatchers.Main.immediate) {
            val result = when (val jokesResult = fetchJokesByQueryUseCase.fetchJokes(query)) {
                FetchJokesByQueryResult.Error -> Error
                is FetchJokesByQueryResult.Success -> Success(jokesResult.jokes)

            }
            _jokesByQueryFlow.value = result
        }
    }

    fun setScreenTitle(@StringRes title: Int) {
        setScreenTitleUseCase.setScreenTitle(title)
    }
}
