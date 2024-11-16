package com.szpejsoft.chucknorrisjokes.joke

import com.szpejsoft.chucknorrisjokes.networking.joke.JokeSchema

fun JokeSchema.toJoke(): Joke = Joke(
    id = id,
    url = url,
    value = value
)

fun List<JokeSchema>.toJokes(): List<Joke> = map { it.toJoke() }