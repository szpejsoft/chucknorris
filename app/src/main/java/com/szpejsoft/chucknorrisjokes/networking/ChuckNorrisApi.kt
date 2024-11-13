package com.szpejsoft.chucknorrisjokes.networking

import com.szpejsoft.chucknorrisjokes.networking.joke.JokeSchema
import retrofit2.http.GET

interface ChuckNorrisApi {
    @GET("jokes/random")
    suspend fun getRandomJoke(): JokeSchema?
}