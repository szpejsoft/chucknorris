package com.szpejsoft.chucknorrisjokes.screens.categories

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import com.szpejsoft.chucknorrisjokes.category.FetchCategoriesUseCase
import com.szpejsoft.chucknorrisjokes.common.screentitle.SetScreenTitleUseCase
import com.szpejsoft.chucknorrisjokes.screens.categories.CategoriesViewModel.CategoriesResult.Error
import com.szpejsoft.chucknorrisjokes.screens.categories.CategoriesViewModel.CategoriesResult.None
import com.szpejsoft.chucknorrisjokes.screens.categories.CategoriesViewModel.CategoriesResult.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel
@Inject
constructor(
    private val fetchCategoriesUseCase: FetchCategoriesUseCase,
    private val setScreenTitleUseCase: SetScreenTitleUseCase
) : ViewModel() {

    sealed class CategoriesResult {
        data object None : CategoriesResult()
        data class Success(val categories: List<String>) : CategoriesResult()
        data object Error : CategoriesResult()
    }

    val categoriesFlow: StateFlow<CategoriesResult> get() = _categoriesFlow
    private val _categoriesFlow = MutableStateFlow<CategoriesResult>(None)

    suspend fun fetchCategories(){
        withContext(Dispatchers.Main.immediate){
            val result = when(val useCaseResult = fetchCategoriesUseCase.fetchCategories()){
                FetchCategoriesUseCase.FetchCategoriesResult.Error -> Error
                is FetchCategoriesUseCase.FetchCategoriesResult.Success -> Success(useCaseResult.categories)
            }
            _categoriesFlow.value = result
        }
    }

    fun setScreenTitle(@StringRes title: Int) {
        setScreenTitleUseCase.setScreenTitle(title)
    }

}