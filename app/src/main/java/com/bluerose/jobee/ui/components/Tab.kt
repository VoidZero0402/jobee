package com.bluerose.jobee.ui.components

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.bluerose.jobee.R
import com.bluerose.jobee.ui.utils.AnimationDuration
import com.bluerose.jobee.ui.utils.getThemeColor

class Tab @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.tabStyle
) : AppCompatTextView(context, attrs, defStyleAttr) {
    private var isActiveState: Boolean = false

    private val colorPrimary = context.getThemeColor(R.attr.colorPrimary)
    private val colorNeutral = context.getThemeColor(R.attr.colorNeutral)
    private val evaluator = ArgbEvaluator()

    private fun applyStateAnimation() {
        val fromColor = if (isActiveState) colorNeutral else colorPrimary
        val toColor = if (isActiveState) colorPrimary else colorNeutral
        val animator = ValueAnimator.ofObject(ArgbEvaluator(), fromColor, toColor)
        animator.addUpdateListener {
            setTextColor(it.animatedValue as Int)
        }
        animator.duration = AnimationDuration.MEDIUM.duration.toLong()
        animator.start()
    }

    private fun applyStateInstant() {
        setTextColor(if (isActiveState) colorPrimary else colorNeutral)
    }

    fun applyEvaluatedColor(fraction: Float) {
        setTextColor(evaluator.evaluate(fraction, colorPrimary, colorNeutral) as Int)
    }

    fun activate() {
        isActiveState = true
        applyStateInstant()
    }

    fun activateAnimated() {
        isActiveState = true
        applyStateAnimation()
    }

    fun deactivate() {
        isActiveState = false
        applyStateInstant()
    }

    fun deactivateAnimated() {
        isActiveState = false
        applyStateAnimation()
    }
}