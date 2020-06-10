package com.example.premierleagueapp.core.presentation.extensions

import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar

fun View.showSnack(
    message: String, length: Int = Snackbar.LENGTH_LONG,
    doAction: Snackbar.() -> Unit
) {
    val snack = Snackbar.make(this, message, length)
    snack.doAction()
    snack.view.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
    snack.show()
}

fun Snackbar.action(action: String, color: Int? = null, listener: (View) -> Unit) {
    setAction(action, listener)
    color?.let { setActionTextColor(color) }
}

