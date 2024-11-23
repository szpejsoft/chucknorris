package com.szpejsoft.chucknorrisjokes.screens.jokebycategory

import androidx.lifecycle.ViewModel
import com.szpejsoft.chucknorrisjokes.joke.FetchRandomJokeByCategoryUseCase
import com.szpejsoft.chucknorrisjokes.joke.Joke
import com.szpejsoft.chucknorrisjokes.screens.jokebycategory.JokeByCategoryViewModel.JokeByCategoryResult.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.String
import com.szpejsoft.chucknorrisjokes.joke.FetchRandomJokeByCategoryUseCase.FetchJokeByCategoryResult.Error as UseCaseError
import com.szpejsoft.chucknorrisjokes.joke.FetchRandomJokeByCategoryUseCase.FetchJokeByCategoryResult.Success as UseCaseSuccess

@HiltViewModel
class JokeByCategoryViewModel
@Inject
constructor(
    private val fetchRandomJokeByCategoryUseCase: FetchRandomJokeByCategoryUseCase
) : ViewModel() {

    sealed class JokeByCategoryResult {
        data class Success(val joke: Joke) : JokeByCategoryResult()
        data object Error : JokeByCategoryResult()
        data object None : JokeByCategoryResult()
    }

    val jokeByCategoryFlow: StateFlow<JokeByCategoryResult> get() = _jokeByCategoryFlow
    private val _jokeByCategoryFlow = MutableStateFlow<JokeByCategoryResult>(None)

    suspend fun fetchJokeByCategory(category: String) {
        withContext(Dispatchers.Main.immediate) {
            val result = when (val jokeResult = fetchRandomJokeByCategoryUseCase.fetchRandomJokeByCategory(category)) {
                UseCaseError -> Error
                is UseCaseSuccess -> Success(jokeResult.joke)
            }
            _jokeByCategoryFlow.value = result
        }
    }
}