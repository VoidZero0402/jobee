package com.bluerose.jobee.ui.utils

import android.content.Context
import android.content.res.Resources

object Dimensions {
    private val systemMetrics = Resources.getSystem().displayMetrics
    private val systemConfig = Resources.getSystem().configuration

    val Int.dp: Float
        get() = this * systemMetrics.density

    fun Int.dp(context: Context): Float = this * context.resources.displayMetrics.density

    val Int.sp: Float
        get() = this * systemMetrics.density * systemConfig.fontScale

    fun Int.sp(context: Context): Float {
        val metrics = context.resources.displayMetrics
        val fontScale = context.resources.configuration.fontScale
        return this * metrics.density * fontScale
    }

    val Float.dp: Float
        get() = this * systemMetrics.density

    fun Float.dp(context: Context): Float = this * context.resources.displayMetrics.density

    val Float.sp: Float
        get() = this * systemMetrics.density * systemConfig.fontScale

    fun Float.sp(context: Context): Float {
        val metrics = context.resources.displayMetrics
        val fontScale = context.resources.configuration.fontScale
        return this * metrics.density * fontScale
    }
}
