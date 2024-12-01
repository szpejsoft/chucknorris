package com.szpejsoft.chucknorrisjokes.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.szpejsoft.chucknorrisjokes.joke.FavouriteJoke

@Database(
    entities = [
        FavouriteJoke::class
    ],
    exportSchema = false,
    version = 1
)

abstract class JokeDatabase : RoomDatabase() {
    abstract val favouriteJokeDao: FavouriteJokeDao
}