package com.bluerose.jobee.ui.components

import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.widget.Checkable
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.withStyledAttributes
import com.bluerose.jobee.R
import com.bluerose.jobee.ui.utils.AnimationDuration
import com.bluerose.jobee.ui.utils.Dimensions.dp
import com.bluerose.jobee.ui.utils.getThemeColor

class Chip @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.chipStyle
) : AppCompatTextView(context, attrs, defStyleAttr), Checkable {
    private var isCheckedState = false
    private var isCheckable = true
    private var animationDuration = 0L

    private var onCheckedChanged: ((checked: Boolean) -> Unit)? = null

    private val colorPrimary = context.getThemeColor(R.attr.colorPrimary)
    private val colorWhite = context.getColor(R.color.white)
    private lateinit var backgroundDrawable: GradientDrawable

    private data class AnimatedStateConfig(
        val backgroundColor: Pair<Int, Int>,
        val textColor: Pair<Int, Int>
    )

    init {
        isClickable = true
        isFocusable = true
        context.withStyledAttributes(attrs, R.styleable.Chip, defStyleAttr, 0) {
            isCheckedState = getBoolean(R.styleable.Chip_checked, false)
            isCheckable = getBoolean(R.styleable.Chip_checkable, true)
            animationDuration = getInt(
                R.styleable.TextField_animationDuration,
                AnimationDuration.SHORT.duration
            ).toLong()
        }
        setupView()
        setupListeners()
    }

    private fun setupView() {
        backgroundDrawable = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 100f.dp
            setColor(if (isCheckedState) colorPrimary else Color.TRANSPARENT)
            setStroke(2.dp, colorPrimary)
        }
        background = backgroundDrawable
        setTextColor(if (isCheckedState) colorWhite else colorPrimary)
    }

    private fun setupListeners() {
        if (isCheckable) {
            setOnClickListener {
                toggle()
            }
        }
    }

    private fun applyState() {
        onCheckedChanged?.invoke(isCheckedState)
        applyStateAnimation()
    }

    private fun getAnimatedStateConfig(): AnimatedStateConfig {
        return when {
            isCheckedState -> AnimatedStateConfig(
                Color.TRANSPARENT to colorPrimary,
                colorPrimary to colorWhite
            )

            else -> AnimatedStateConfig(
                colorPrimary to Color.TRANSPARENT,
                colorWhite to colorPrimary
            )
        }
    }

    private fun applyStateAnimation() {
        val (backgroundColor, textColor) = getAnimatedStateConfig()

        val backgroundColorAnimator = ValueAnimator.ofObject(
            ArgbEvaluator(),
            backgroundColor.first,
            backgroundColor.second
        )
        backgroundColorAnimator.addUpdateListener {
            backgroundDrawable.setColor(it.animatedValue as Int)
        }

        val textColorAnimator = ValueAnimator.ofObject(ArgbEvaluator(), textColor.first, textColor.second)
        textColorAnimator.addUpdateListener {
            setTextColor(it.animatedValue as Int)
        }

        val animatorSet = AnimatorSet()
        animatorSet.duration = animationDuration
        animatorSet.playTogether(backgroundColorAnimator, textColorAnimator)
        animatorSet.start()
    }

    fun setOnCheckedChangedListener(l: (checked: Boolean) -> Unit) {
        onCheckedChanged = l
    }

    override fun setChecked(checked: Boolean) {
        if (isCheckedState != checked) {
            isCheckedState = checked
            applyState()
        }
    }

    override fun isChecked(): Boolean = isCheckedState

    override fun toggle() {
        isCheckedState = !isCheckedState
        applyState()
    }
}