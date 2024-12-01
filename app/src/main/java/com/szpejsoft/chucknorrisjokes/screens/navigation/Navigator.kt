package com.szpejsoft.chucknorrisjokes.screens.navigation

import android.util.Log
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
    val currentRoute: StateFlow<Route?> get() = _currentRoute
    val currentTab: StateFlow<BottomBarTab?> get() = _currentTab
    val isRootRoute: StateFlow<Boolean> get() = _isRootRoute

    private val _currentRoute = MutableStateFlow<Route?>(null)
    private val _currentTab = MutableStateFlow<BottomBarTab?>(null)
    private val _isRootRoute = MutableStateFlow(false)

    private val scope = CoroutineScope(Dispatchers.Main.immediate)
    private var tabNavControllerObserveJob: Job? = null
    private var nestedNavControllerObserveJob: Job? = null

    private lateinit var tabNavController: NavHostController
    private var nestedNavController: NavHostController? = null

    fun setTabNavController(navHostController: NavHostController) {
        tabNavController = navHostController
        tabNavControllerObserveJob?.cancel()
        tabNavControllerObserveJob = scope.launch {
            navHostController.currentBackStackEntryFlow
                .map { backStackEntry ->
                    Log.d("ptsz", "backstackentryflow: ${backStackEntry.destination}")
                    val bottomTab = when (val routeName = backStackEntry.destination.route) {
                        Route.RandomJoke.routeName -> BottomBarTab.Random
                        Route.JokesByQuery.routeName -> BottomBarTab.Query
                        Route.Categories.routeName -> BottomBarTab.Categories
                        Route.Favourites.routeName -> BottomBarTab.Favourites
                        null -> null
                        else -> throw RuntimeException("Unknown route $routeName")
                    }
                    _currentTab.value = bottomTab
                }
                .collect()
        }
    }

    fun setNestedNavController(navHostController: NavHostController) {
        nestedNavController = navHostController
        nestedNavControllerObserveJob?.cancel()
        nestedNavControllerObserveJob = scope.launch {
            navHostController.currentBackStackEntryFlow
                .map { backStackEntry ->
                    when (val routeName = backStackEntry.destination.route) {
                        Route.Categories.routeName -> Route.Categories
                        Route.RandomJoke.routeName -> Route.RandomJoke
                        Route.JokesByQuery.routeName -> Route.JokesByQuery
                        Route.JokeByCategory().routeName -> {
                            val args = backStackEntry.arguments
                            Route.JokeByCategory(args?.getString("category")!!)
                        }

                        Route.Favourites.routeName -> Route.Favourites
                        null -> null
                        else -> throw RuntimeException("Unknown route $routeName")
                    }
                }
                .collect { route ->
                    _currentRoute.value = route
                    _isRootRoute.value = route == Route.RandomJoke
                }
        }
    }

    fun toTab(bottomBarTab: BottomBarTab) {
        val route = when (bottomBarTab) {
            BottomBarTab.Query -> Route.JokesByQuery
            BottomBarTab.Random -> Route.RandomJoke
            BottomBarTab.Categories -> Route.Categories
            BottomBarTab.Favourites -> Route.Favourites
        }

        tabNavController.navigate(route.routeName) {
            tabNavController.graph.startDestinationRoute?.let { startRoute ->
                popUpTo(startRoute) {
                    saveState = true
                }
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun toRoute(route: Route) {
        nestedNavController?.navigate(route.navCommand)
    }

    fun navigateBack() {
        if (nestedNavController?.popBackStack() == false) tabNavController.popBackStack()
    }

    companion object {
        val BOTTOM_TABS = listOf(
            BottomBarTab.Random,
            BottomBarTab.Query,
            BottomBarTab.Categories,
            BottomBarTab.Favourites
        )
    }

}