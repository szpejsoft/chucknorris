package com.szpejsoft.chucknorrisjokes.joke

import com.szpejsoft.chucknorrisjokes.networking.ChuckNorrisApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchRandomJokeUseCase
@Inject
constructor(private val chuckNorrisApi: ChuckNorrisApi) {

    sealed class FetchRandomJokeResult {
        data class Success(val joke: Joke) : FetchRandomJokeResult()
        data object Error : FetchRandomJokeResult()
    }

    suspend fun fetchRandomJoke(): FetchRandomJokeResult {
        return withContext(Dispatchers.IO) {
            val jokeSchema = chuckNorrisApi.getRandomJoke()
            jokeSchema
                ?.run { FetchRandomJokeResult.Success(jokeSchema.toJoke()) }
                ?: FetchRandomJokeResult.Error
        }
    }
}
