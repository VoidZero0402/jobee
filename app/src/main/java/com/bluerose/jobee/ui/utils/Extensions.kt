package com.bluerose.jobee.ui.utils

import android.content.Context
import android.util.TypedValue

fun Context.getThemeColor(attrResId: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(attrResId, typedValue, true)
    return typedValue.data
}