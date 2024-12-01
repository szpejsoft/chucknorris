package com.szpejsoft.chucknorrisjokes.screens.randomjoke

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import com.szpejsoft.chucknorrisjokes.common.screentitle.SetScreenTitleUseCase
import com.szpejsoft.chucknorrisjokes.joke.FetchRandomJokeUseCase
import com.szpejsoft.chucknorrisjokes.joke.FetchRandomJokeUseCase.FetchRandomJokeResult
import com.szpejsoft.chucknorrisjokes.joke.Joke
import com.szpejsoft.chucknorrisjokes.screens.randomjoke.RandomJokeViewModel.RandomJokeResult.Error
import com.szpejsoft.chucknorrisjokes.screens.randomjoke.RandomJokeViewModel.RandomJokeResult.None
import com.szpejsoft.chucknorrisjokes.screens.randomjoke.RandomJokeViewModel.RandomJokeResult.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RandomJokeViewModel
@Inject
constructor(
    private val fetchRandomJokeUseCase: FetchRandomJokeUseCase,
    private val setScreenTitleUseCase: SetScreenTitleUseCase
) : ViewModel() {
    sealed class RandomJokeResult {
        data object None : RandomJokeResult()
        data class Success(val joke: Joke) : RandomJokeResult()
        data class Error(val message: String) : RandomJokeResult()
    }

    private val _randomJokeFlow = MutableStateFlow<RandomJokeResult>(None)
    val randomJokeFlow: StateFlow<RandomJokeResult> = _randomJokeFlow

    suspend fun fetchRandomJoke() {
        withContext(Dispatchers.Main.immediate) {
            val result = when (val jokeResult = fetchRandomJokeUseCase.fetchRandomJoke()) {
                FetchRandomJokeResult.Error -> Error("Something went wrong, no joke for you :(")
                is FetchRandomJokeResult.Success -> Success(jokeResult.joke)
            }
            _randomJokeFlow.value = result
        }
    }

    fun setScreenTitle(@StringRes title: Int) {
        setScreenTitleUseCase.setScreenTitle(title)
    }

}