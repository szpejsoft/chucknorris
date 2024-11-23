package com.szpejsoft.chucknorrisjokes.networking.category

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CategoriesSchema(
    val categories: List<String>
)