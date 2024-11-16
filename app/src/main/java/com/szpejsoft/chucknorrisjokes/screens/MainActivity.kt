package com.szpejsoft.chucknorrisjokes.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.szpejsoft.chucknorrisjokes.screens.jokesbyquery.JokesByQueryScreen
import com.szpejsoft.chucknorrisjokes.screens.randomjoke.RandomJokeScreen
import com.szpejsoft.chucknorrisjokes.screens.theme.Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Theme {
                JokesByQueryScreen()
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Theme {
        RandomJokeScreen()
    }
}