package com.szpejsoft.chucknorrisjokes.joke

import com.szpejsoft.chucknorrisjokes.joke.FetchRandomJokeByCategoryUseCase.FetchJokeByCategoryResult.Error
import com.szpejsoft.chucknorrisjokes.joke.FetchRandomJokeByCategoryUseCase.FetchJokeByCategoryResult.Success
import com.szpejsoft.chucknorrisjokes.networking.ChuckNorrisApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchRandomJokeByCategoryUseCase
@Inject
constructor(private val chuckNorrisApi: ChuckNorrisApi) {

    sealed class FetchJokeByCategoryResult {
        data class Success(val joke: Joke) : FetchJokeByCategoryResult()
        data object Error : FetchJokeByCategoryResult()
    }

    suspend fun fetchRandomJokeByCategory(category: String): FetchJokeByCategoryResult {
        return withContext(Dispatchers.IO) {
            val jokeSchema = chuckNorrisApi.getRandomJokeByCategory(category)
            jokeSchema
                ?.run { Success(jokeSchema.toJoke()) }
                ?: Error
        }
    }
}
