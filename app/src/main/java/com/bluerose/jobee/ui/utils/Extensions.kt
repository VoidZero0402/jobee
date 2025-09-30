package com.bluerose.jobee.ui.utils

import android.content.Context
import android.content.ContextWrapper
import android.util.TypedValue
import android.view.View
import androidx.activity.ComponentActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

fun Context.getThemeColor(attrResId: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(attrResId, typedValue, true)
    return typedValue.data
}

fun Context.preformBackNavigation() {
    when (this) {
        is ComponentActivity -> {
            onBackPressedDispatcher.onBackPressed()
        }

        is ContextWrapper -> {
            baseContext.preformBackNavigation()
        }
    }
}

fun View.applySystemTopInsets() {
    val initialPadding = paddingTop
    ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        v.setPadding(v.paddingLeft, initialPadding + systemBars.top, v.paddingRight, paddingBottom)
        insets
    }
    ViewCompat.requestApplyInsets(this)
}

fun View.applySystemBottomInsets() {
    val initialPadding = paddingBottom
    ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        v.setPadding(v.paddingLeft, v.paddingTop, v.paddingRight, initialPadding + systemBars.bottom)
        insets
    }
    ViewCompat.requestApplyInsets(this)
}