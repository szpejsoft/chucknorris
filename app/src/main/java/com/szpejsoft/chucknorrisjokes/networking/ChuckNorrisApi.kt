package com.szpejsoft.chucknorrisjokes.networking

import com.szpejsoft.chucknorrisjokes.networking.category.CategoriesSchema
import com.szpejsoft.chucknorrisjokes.networking.joke.JokeListSchema
import com.szpejsoft.chucknorrisjokes.networking.joke.JokeSchema
import retrofit2.http.GET
import retrofit2.http.Query

interface ChuckNorrisApi {

    @GET("jokes/random")
    suspend fun getRandomJoke(): JokeSchema?

    @GET("/jokes/random")
    suspend fun getRandomJokeByCategory(@Query("category") category: String): JokeSchema?

    @GET("jokes/search")
    suspend fun getJokesByQuery(@Query("query") query: String): JokeListSchema?

    @GET("/jokes/categories")
    suspend fun getCategories(): CategoriesSchema?

}
