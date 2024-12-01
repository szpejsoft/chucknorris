package com.szpejsoft.chucknorrisjokes.common.screentitle

import androidx.annotation.StringRes

class Title {
    @StringRes
    val resId: Int?
    val text: String?

    constructor(@StringRes resId: Int) : this(resId, null)
    constructor(text: String) : this(null, text)

    private constructor (resId: Int?, text: String?) {
        this.resId = resId
        this.text = text
    }

    val hasText: Boolean get() = text != null

    override fun toString(): String {
        return "Title(resId=$resId, text=$text)"
    }
}