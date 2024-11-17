package com.szpejsoft.chucknorrisjokes.screens.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.szpejsoft.chucknorrisjokes.screens.jokesbyquery.JokesByQueryScreen
import com.szpejsoft.chucknorrisjokes.screens.navigation.BottomNavBar
import com.szpejsoft.chucknorrisjokes.screens.navigation.Navigator
import com.szpejsoft.chucknorrisjokes.screens.navigation.Navigator.Companion.BOTTOM_TABS
import com.szpejsoft.chucknorrisjokes.screens.navigation.Route
import com.szpejsoft.chucknorrisjokes.screens.randomjoke.RandomJokeScreen

@Composable
fun MainScreen() {

    val navigator = remember { Navigator() }
    val currentBottomTab = navigator.currentTab.collectAsState().value


    Scaffold(
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
    val navController = rememberNavController()
    navigator.setNavController(navController)

    Surface(
        modifier = Modifier
            .padding(padding)
            .padding(horizontal = 12.dp)
    ) {
        NavHost(
            modifier = Modifier.fillMaxSize(),
            navController = navController,
            startDestination = Route.RandomJoke.routeName
        ) {
            composable(Route.RandomJoke.routeName) {
                RandomJokeScreen()
            }
            composable(Route.JokesByQuery.routeName) {
                JokesByQueryScreen()
            }
        }
    }
}
