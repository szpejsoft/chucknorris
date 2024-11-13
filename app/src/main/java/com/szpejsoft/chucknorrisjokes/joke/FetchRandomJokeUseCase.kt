package com.szpejsoft.chucknorrisjokes.joke

import com.szpejsoft.chucknorrisjokes.networking.ChuckNorrisApi
import com.szpejsoft.chucknorrisjokes.networking.joke.JokeSchema
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchRandomJokeUseCase @Inject constructor(
    private val chuckNorrisApi: ChuckNorrisApi
) {

    sealed class RandomJokeResult {
        data class Success(val joke: Joke) : RandomJokeResult()
        data object Error : RandomJokeResult()
    }

    suspend fun fetchRandomJoke(): RandomJokeResult {
        return withContext(Dispatchers.IO) {
            val jokeSchema = chuckNorrisApi.getRandomJoke()
            if (jokeSchema != null) {
                RandomJokeResult.Success(jokeSchema.toJoke())
            } else {
                RandomJokeResult.Error
            }
        }
    }

    private fun JokeSchema.toJoke(): Joke = Joke(
        id = id,
        url = url,
        value = value
    )
}