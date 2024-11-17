package com.szpejsoft.chucknorrisjokes.screens.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.szpejsoft.chucknorrisjokes.screens.navigation.BottomBarTab
import com.szpejsoft.chucknorrisjokes.screens.navigation.BottomNavBar
import com.szpejsoft.chucknorrisjokes.screens.randomjoke.RandomJokeScreen

@Composable
fun MainScreen() {
    Scaffold(
        content = { padding ->
            MainScreenContent(padding)
        },
        bottomBar = {
            BottomNavBar(
                tabs = listOf(BottomBarTab.Random, BottomBarTab.Query),
                selectedTab = BottomBarTab.Random,
                onTabClicked = { /*TBD when screen navigator is implemented*/ }
            )
        }
    )
}

@Composable
fun MainScreenContent(
    padding: PaddingValues
) {
    Surface(
        modifier = Modifier
            .padding(padding)
            .padding(horizontal = 12.dp)
    ) {
        RandomJokeScreen()
    }
}