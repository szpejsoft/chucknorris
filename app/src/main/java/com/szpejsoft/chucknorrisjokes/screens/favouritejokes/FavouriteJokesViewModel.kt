package com.szpejsoft.chucknorrisjokes.screens.favouritejokes

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import com.szpejsoft.chucknorrisjokes.common.screentitle.SetScreenTitleUseCase
import com.szpejsoft.chucknorrisjokes.joke.usecases.ObserveFavouriteJokesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavouriteJokesViewModel
@Inject
constructor(
    private val observeFavouriteJokesUseCase: ObserveFavouriteJokesUseCase,
    private val setScreenTitleUseCase: SetScreenTitleUseCase
) : ViewModel() {

    fun observeFavouriteJokes() = observeFavouriteJokesUseCase.observeFavouriteJokes()

    fun setScreenTitle(@StringRes title: Int) {
        setScreenTitleUseCase.setScreenTitle(title)
    }

}
