package com.szpejsoft.chucknorrisjokes.screens.categories

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import com.szpejsoft.chucknorrisjokes.screens.categories.CategoriesViewModel.CategoriesResult.Error
import com.szpejsoft.chucknorrisjokes.screens.categories.CategoriesViewModel.CategoriesResult.None
import com.szpejsoft.chucknorrisjokes.screens.categories.CategoriesViewModel.CategoriesResult.Success
import com.szpejsoft.chucknorrisjokes.screens.composables.AlertDialogWithButton
import com.szpejsoft.chucknorrisjokes.screens.composables.ShowInitialState
import com.szpejsoft.chucknorrisjokes.screens.composables.ShowList
import kotlinx.coroutines.launch


//TODO add pull to refresh
@Composable
fun CategoriesScreen(
    viewModel: CategoriesViewModel = hiltViewModel(),
    onCategoryClicked: (String) -> Unit
) {
    val scope = rememberCoroutineScope()
    val categoriesResult = viewModel.categoriesFlow.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.fetchCategories()
    }

    when (categoriesResult) {
        is Success -> ShowList(elements = categoriesResult.categories, onElementClicked = onCategoryClicked)
        is Error -> AlertDialogWithButton(
            message = "No categories for you :(. Try again later.",
            buttonText = "Ok"
        ) { scope.launch { viewModel.fetchCategories() } }

        is None -> ShowInitialState()

    }
}
