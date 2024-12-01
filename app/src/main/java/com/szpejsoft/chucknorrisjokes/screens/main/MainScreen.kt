package com.szpejsoft.chucknorrisjokes.screens.main

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.szpejsoft.chucknorrisjokes.R
import com.szpejsoft.chucknorrisjokes.common.screentitle.Title
import com.szpejsoft.chucknorrisjokes.screens.categories.CategoriesScreen
import com.szpejsoft.chucknorrisjokes.screens.composables.TopBar
import com.szpejsoft.chucknorrisjokes.screens.jokebycategory.JokeByCategoryScreen
import com.szpejsoft.chucknorrisjokes.screens.jokesbyquery.JokesByQueryScreen
import com.szpejsoft.chucknorrisjokes.screens.navigation.BottomNavBar
import com.szpejsoft.chucknorrisjokes.screens.navigation.Navigator
import com.szpejsoft.chucknorrisjokes.screens.navigation.Navigator.Companion.BOTTOM_TABS
import com.szpejsoft.chucknorrisjokes.screens.navigation.Route
import com.szpejsoft.chucknorrisjokes.screens.randomjoke.RandomJokeScreen
import kotlinx.coroutines.flow.map

@Composable
fun MainScreen(
    mainViewModel: MainViewModel = hiltViewModel()
) {

    val navigator = remember { Navigator() }
    val currentBottomTab = navigator.currentTab.collectAsState().value
    val appTitle = mainViewModel.observeScreenTitle().collectAsState().value

    Scaffold(
        topBar = {
            TopBar(
                title = getScreenTitleText(appTitle),
                isRootRoute = navigator.isRootRoute.collectAsState().value,
                onBackClicked = { navigator.navigateBack() }
            )
        },
        content = { padding ->
            MainScreenContent(padding, navigator)
        },
        bottomBar = {
            BottomNavBar(
                tabs = BOTTOM_TABS,
                selectedTab = currentBottomTab,
                onTabClicked = { bottomBarTab -> navigator.toTab(bottomBarTab) }
            )
        }
    )
}

@Composable
fun MainScreenContent(
    padding: PaddingValues,
    navigator: Navigator
) {
    val tabNavController = rememberNavController()
    navigator.setTabNavController(tabNavController)

    Surface(
        modifier = Modifier
            .padding(padding)
            .padding(horizontal = 12.dp)
    ) {
        NavHost(
            modifier = Modifier.fillMaxSize(),
            navController = tabNavController,
            startDestination = Route.RandomJoke.routeName
        ) {
            composable(Route.RandomJoke.routeName) {
                val randomJokeNavController = rememberNavController()
                navigator.setNestedNavController(randomJokeNavController)
                NavHost(
                    navController = randomJokeNavController,
                    startDestination = Route.RandomJoke.routeName
                ) {
                    composable(Route.RandomJoke.routeName) {
                        RandomJokeScreen()
                    }
                }
            }
            composable(Route.JokesByQuery.routeName) {
                val jokesByQueryNavController = rememberNavController()
                navigator.setNestedNavController(jokesByQueryNavController)
                NavHost(
                    navController = jokesByQueryNavController,
                    startDestination = Route.JokesByQuery.routeName
                ) {
                    composable(route = Route.JokesByQuery.routeName) {
                        JokesByQueryScreen()
                    }
                }
            }
            composable(Route.Categories.routeName) {
                val nestedNavController = rememberNavController()
                navigator.setNestedNavController(nestedNavController)
                NavHost(
                    navController = nestedNavController,
                    startDestination = Route.Categories.routeName
                ) {
                    composable(route = Route.Categories.routeName) {
                        CategoriesScreen(
                            onCategoryClicked = { category -> navigator.toRoute(Route.JokeByCategory(category)) }
                        )
                    }
                    composable(route = Route.JokeByCategory().routeName) {
                        val category = remember { it.arguments?.getString("category")!! }
                        JokeByCategoryScreen(category = category)
                    }
                }
            }
        }
    }
}

@Composable
fun getScreenTitleText(title: Title): String {
    Log.d("ptsz", "getScreenTitleText: $title ")
    return if (title.hasText) title.text!! else stringResource(id = title.resId!!)
}
