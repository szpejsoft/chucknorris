package com.szpejsoft.chucknorrisjokes.common.screentitle

import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class ObserveScreenTitleUseCase
@Inject
constructor(private val appBarTitleManager: AppBarTitleManager) {
    fun observeScreenTitle(): StateFlow<Title> = appBarTitleManager.screenTitleFlow
}