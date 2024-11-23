package com.szpejsoft.chucknorrisjokes.networking.category

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test


class SerializationTest {

    @Test
    fun `when two categories in list then two categories are in schema`() {
        //arrange
        val categories = "[\"animal\" , \"career\"]"

        //act
        val categoriesSchema = CategoriesAdapter.fromJson(categories)

        //assert
        assertThat(categoriesSchema?.categories?.get(0)).isEqualTo("animal")
        assertThat(categoriesSchema?.categories?.get(1)).isEqualTo("career")
    }

    @Test
    fun `when zero categories in list then zero categories are in schema`() {
        //arrange
        val categories = "[]"

        //act
        val categoriesSchema = CategoriesAdapter.fromJson(categories)

        //assert
        assertThat(categoriesSchema?.categories?.isEmpty()).isTrue()
    }

}