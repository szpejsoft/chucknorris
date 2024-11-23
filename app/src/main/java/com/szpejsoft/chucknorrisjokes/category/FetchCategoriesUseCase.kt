package com.szpejsoft.chucknorrisjokes.category

import com.szpejsoft.chucknorrisjokes.category.FetchCategoriesUseCase.FetchCategoriesResult.Error
import com.szpejsoft.chucknorrisjokes.category.FetchCategoriesUseCase.FetchCategoriesResult.Success
import com.szpejsoft.chucknorrisjokes.networking.ChuckNorrisApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchCategoriesUseCase
@Inject
constructor(private val api: ChuckNorrisApi) {

    sealed class FetchCategoriesResult {
        data class Success(val categories: List<String>) : FetchCategoriesResult()
        data object Error : FetchCategoriesResult()
    }

    suspend fun fetchCategories(): FetchCategoriesResult {
        return withContext(Dispatchers.IO) {
            val categoriesSchema = api.getCategories()
            categoriesSchema
                ?.run { Success(categoriesSchema.categories) }
                ?: Error
        }
    }
}
