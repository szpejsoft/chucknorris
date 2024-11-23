package com.szpejsoft.chucknorrisjokes.screens.navigation

sealed class Route(val routeName: String) {
    data object RandomJoke : Route("randomJoke")
    data object JokesByQuery : Route("jokesByQuery")
    data object Categories : Route("categories")
    data class JokeByCategory(val category: String = "") : Route("jokeByCategory/{category}") {
        override val navCommand: String
            get() = routeName.replace("{category}", category)
    }

    open val navCommand = routeName
}