package com.szpejsoft.chucknorrisjokes.joke

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_joke")
data class FavouriteJoke(
    @PrimaryKey val id: String,
    val iconUrl: String,
    val value: String
)