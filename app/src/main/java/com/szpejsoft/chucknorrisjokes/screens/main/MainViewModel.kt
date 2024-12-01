package com.szpejsoft.chucknorrisjokes.screens.main

import androidx.lifecycle.ViewModel
import com.szpejsoft.chucknorrisjokes.common.screentitle.ObserveScreenTitleUseCase
import com.szpejsoft.chucknorrisjokes.common.screentitle.Title
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(private val observeScreenTitleUseCase: ObserveScreenTitleUseCase) : ViewModel() {
    fun observeScreenTitle(): StateFlow<Title> = observeScreenTitleUseCase.observeScreenTitle()
}
