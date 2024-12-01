package com.szpejsoft.chucknorrisjokes.screens.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarTab(val icon: ImageVector, val title: String) {
    data object Random : BottomBarTab(Icons.Filled.Refresh, "Random")
    data object Query : BottomBarTab(Icons.Filled.Search, "Query")
    data object Categories : BottomBarTab(Icons.Filled.Settings, "Categories")
    data object Favourites: BottomBarTab(Icons.Filled.Star, "Favourites")
}
