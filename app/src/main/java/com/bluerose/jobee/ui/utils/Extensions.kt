package com.bluerose.jobee.ui.utils

import android.content.Context
import android.content.ContextWrapper
import android.util.TypedValue
import androidx.activity.ComponentActivity

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