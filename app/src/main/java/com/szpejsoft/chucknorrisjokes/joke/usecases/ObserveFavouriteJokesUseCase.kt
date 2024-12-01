package com.szpejsoft.chucknorrisjokes.joke.usecases

import com.szpejsoft.chucknorrisjokes.database.FavouriteJokeDao
import javax.inject.Inject

class ObserveFavouriteJokesUseCase
@Inject
constructor(private val favouriteJokeDao: FavouriteJokeDao) {
    fun observeFavouriteJokes() = favouriteJokeDao.observeFavouriteJokes()
}