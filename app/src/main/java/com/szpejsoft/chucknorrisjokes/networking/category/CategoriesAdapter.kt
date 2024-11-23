package com.szpejsoft.chucknorrisjokes.networking.category

import android.util.Log
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson

object CategoriesAdapter : JsonAdapter<CategoriesSchema>() {

    @FromJson
    override fun fromJson(reader: JsonReader): CategoriesSchema? {
        try {
            val categories = mutableListOf<String>()
            reader.beginArray()
            while (reader.hasNext()) {
                val category = reader.nextString()
                categories.add(category)
            }
            reader.endArray()
            return CategoriesSchema(categories)
        } catch (e: Exception) {
            Log.d("ptsz", "CategoriesAdapter parsing response error: $e")
            return null
        }
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: CategoriesSchema?) {
        writer.beginArray()
        value?.categories?.forEach { category ->
            writer.value(category)
        }
        writer.endArray()
    }
}
