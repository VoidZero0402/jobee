package com.bluerose.jobee.ui.utils

import android.content.Context
import android.content.res.Resources

object Dimensions {
    private val systemMetrics = Resources.getSystem().displayMetrics
    private val systemConfig = Resources.getSystem().configuration

    val Int.dp: Int
        get() = (this * systemMetrics.density).toInt()

    fun Int.dp(context: Context): Int = (this * context.resources.displayMetrics.density).toInt()

    val Int.sp: Int
        get() = (this * systemMetrics.density * systemConfig.fontScale).toInt()

    fun Int.sp(context: Context): Int {
        val metrics = context.resources.displayMetrics
        val fontScale = context.resources.configuration.fontScale
        return (this * metrics.density * fontScale).toInt()
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
