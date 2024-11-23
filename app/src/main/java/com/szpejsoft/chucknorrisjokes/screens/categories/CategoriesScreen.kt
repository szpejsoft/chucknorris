package com.szpejsoft.chucknorrisjokes.screens.categories

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.szpejsoft.chucknorrisjokes.screens.categories.CategoriesViewModel.CategoriesResult.Error
import com.szpejsoft.chucknorrisjokes.screens.categories.CategoriesViewModel.CategoriesResult.None
import com.szpejsoft.chucknorrisjokes.screens.categories.CategoriesViewModel.CategoriesResult.Success
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
        is Success -> ShowCategories(categories = categoriesResult.categories, onCategoryClicked = onCategoryClicked)
        is Error -> ShowError { scope.launch { viewModel.fetchCategories() } }
        is None -> ShowInitialState()

    }
}

@Composable
fun ShowInitialState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Loading...")
    }
}

@Composable
private fun ShowError(
    onOkClicked: () -> Unit
) {
    AlertDialog(
        text = {
            Text("No categories for you :(. Try again later.")
        },
        onDismissRequest = { },
        confirmButton = {
            Button(
                onClick = onOkClicked
            ) {
                Text("OK")
            }
        },
    )
}

@Composable
fun ShowCategories(
    categories: List<String>,
    onCategoryClicked: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 4.dp),
    ) {
        items(categories.size) { index ->
            Box(
                modifier = Modifier
                    .clickable { onCategoryClicked(categories[index]) }
                    .padding(12.dp)
                    .fillMaxSize()
            ) {
                Text(
                    text = categories[index]
                )
            }
            if (index < categories.size - 1) {
                HorizontalDivider(
                    thickness = 1.dp
                )
            }
        }
    }
}
