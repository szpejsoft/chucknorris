package com.szpejsoft.chucknorrisjokes.networking.joke

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class JokeListSchema(
    @Json(name = "total")
    val jokesCount: Int,
    @Json(name = "result")
    val jokes: List<JokeSchema>

)