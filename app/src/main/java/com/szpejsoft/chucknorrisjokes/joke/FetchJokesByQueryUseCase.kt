package com.szpejsoft.chucknorrisjokes.joke

import com.szpejsoft.chucknorrisjokes.joke.FetchJokesByQueryUseCase.FetchJokesByQueryResult.*
import com.szpejsoft.chucknorrisjokes.networking.ChuckNorrisApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchJokesByQueryUseCase
@Inject
constructor(private val chuckNorrisApi: ChuckNorrisApi) {

    sealed class FetchJokesByQueryResult {
        data class Success(val jokes: List<Joke>) : FetchJokesByQueryResult()
        data object Error : FetchJokesByQueryResult()
    }

    suspend fun fetchJokes(query: String): FetchJokesByQueryResult {
        return withContext(Dispatchers.IO) {
            val jokeListSchema = chuckNorrisApi.getJokesByQuery(query)
            jokeListSchema
                ?.run { Success(jokeListSchema.jokes.toJokes()) }
                ?: Error
        }
    }
}
