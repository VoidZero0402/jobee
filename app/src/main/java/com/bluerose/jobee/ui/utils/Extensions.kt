package com.bluerose.jobee.ui.utils

import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.TypedValue
import android.view.View
import androidx.activity.ComponentActivity
import androidx.core.graphics.Insets
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

fun View.applySystemInsets(onApplyInsets: (insets: Insets) -> Unit) {
    ViewCompat.setOnApplyWindowInsetsListener(this) { _, insets ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        onApplyInsets(systemBars)
        insets
    }
    ViewCompat.requestApplyInsets(this)
}

fun View.applySystemTopInsets() {
    val initialPadding = paddingTop
    ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        v.setPaddingTop(initialPadding + systemBars.top)
        insets
    }
    ViewCompat.requestApplyInsets(this)
}

fun View.applySystemBottomInsets() {
    val initialPadding = paddingBottom
    ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        v.setPaddingBottom(initialPadding + systemBars.bottom)
        insets
    }
    ViewCompat.requestApplyInsets(this)
}

fun View.setPaddingTop(padding: Int) {
    setPadding(paddingStart, padding, paddingEnd, paddingBottom)
}

fun View.setPaddingBottom(padding: Int) {
    setPadding(paddingStart, paddingTop, paddingEnd, padding)
}

@Suppress("DEPRECATION")
inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelable(key, T::class.java)
    } else {
        getParcelable(key) as? T
    }
}