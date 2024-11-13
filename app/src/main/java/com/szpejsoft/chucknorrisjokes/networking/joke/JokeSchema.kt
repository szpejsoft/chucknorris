package com.szpejsoft.chucknorrisjokes.networking.joke

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class JokeSchema(
    @Json(name = "id")
    val id: String,
    @Json(name = "categories")
    val categories: List<String>,
    @Json(name = "created_at")
    val createdAt: String,
    @Json(name = "icon_url")
    val iconUrl: String,
    @Json(name = "updated_at")
    val updatedAt: String,
    @Json(name = "url")
    val url: String,
    @Json(name = "value")
    val value: String
)