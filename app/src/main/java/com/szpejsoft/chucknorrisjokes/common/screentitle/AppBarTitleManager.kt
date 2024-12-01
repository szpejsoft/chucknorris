package com.szpejsoft.chucknorrisjokes.common.screentitle

import android.util.Log
import androidx.annotation.StringRes
import com.szpejsoft.chucknorrisjokes.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class AppBarTitleManager {

    val screenTitleFlow: StateFlow<Title> get() = _screenTitleFlow
    private val _screenTitleFlow = MutableStateFlow(Title(R.string.app_name))

    fun setScreenTitle(title: String) {
        Log.d("ptsz", "MainViewModel.setScreenTitle: $title")
        _screenTitleFlow.value = Title(title)
    }

    fun setScreenTitle(@StringRes title: Int) {
        Log.d("ptsz", "MainViewModel.setScreenTitle: $title")
        _screenTitleFlow.value = Title(title)
    }
}