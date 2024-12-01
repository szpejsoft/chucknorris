package com.szpejsoft.chucknorrisjokes.common.screentitle

import androidx.annotation.StringRes
import javax.inject.Inject

class SetScreenTitleUseCase
@Inject
constructor(private val appBarTitleManager: AppBarTitleManager) {

    fun setScreenTitle(title: String) {
        appBarTitleManager.setScreenTitle(title)
    }

    fun setScreenTitle(@StringRes title: Int) {
        appBarTitleManager.setScreenTitle(title)
    }

}