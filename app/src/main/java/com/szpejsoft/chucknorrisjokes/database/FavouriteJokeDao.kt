package com.szpejsoft.chucknorrisjokes.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.szpejsoft.chucknorrisjokes.joke.FavouriteJoke
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteJokeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(favouriteJoke: FavouriteJoke)

    @Query("SELECT * FROM favourite_joke")
    fun observeFavouriteJokes(): Flow<List<FavouriteJoke>>

}