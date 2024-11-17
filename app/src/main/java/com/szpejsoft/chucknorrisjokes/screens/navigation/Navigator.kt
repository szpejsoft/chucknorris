package com.szpejsoft.chucknorrisjokes.screens.navigation

import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class Navigator {
    val currentTab: StateFlow<BottomBarTab> get() = _currentTab

    private val _currentTab = MutableStateFlow<BottomBarTab>(BottomBarTab.Random)

    private val scope = CoroutineScope(Dispatchers.Main.immediate)
    private var navControllerObserveJob: Job? = null


    private lateinit var navController: NavHostController

    fun setNavController(navHostController: NavHostController) {
        navController = navHostController
        navControllerObserveJob?.cancel()
        navControllerObserveJob = scope.launch {
            navHostController.currentBackStackEntryFlow
                .map { backStackEntry ->
                    val bottomTab = when (val routeName = backStackEntry.destination.route) {
                        Route.RandomJoke.routeName -> BottomBarTab.Random
                        Route.JokesByQuery.routeName -> BottomBarTab.Query
                        else -> throw RuntimeException("Unknown route $routeName")
                    }
                    _currentTab.value = bottomTab
                }
                .collect()
        }
    }

    fun toTab(bottomBarTab: BottomBarTab) {
        val route = when (bottomBarTab) {
            BottomBarTab.Query -> Route.JokesByQuery
            BottomBarTab.Random -> Route.RandomJoke
        }

        navController.navigate(route.routeName) {
            navController.graph.startDestinationRoute?.let { startRoute ->
                popUpTo(startRoute) {
                    saveState = true
                }
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    companion object {
        val BOTTOM_TABS = listOf(BottomBarTab.Random, BottomBarTab.Query)
    }

}