package com.szpejsoft.chucknorrisjokes.screens.navigation

sealed class Route(val routeName: String) {
    data object Categories : Route("categories")
    data object Favourites : Route("favourites")
    data object JokesByQuery : Route("jokesByQuery")
    data class JokeByCategory(val category: String = "") : Route("jokeByCategory/{category}") {
        override val navCommand: String
            get() = routeName.replace("{category}", category)
    }

    data object RandomJoke : Route("randomJoke")

    open val navCommand = routeName
}