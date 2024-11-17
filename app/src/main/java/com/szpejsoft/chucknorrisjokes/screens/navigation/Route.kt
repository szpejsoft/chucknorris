package com.szpejsoft.chucknorrisjokes.screens.navigation

sealed class Route(val routeName:String) {
    data object RandomJoke : Route("randomJoke")
    data object JokesByQuery : Route("jokesByQuery")
}