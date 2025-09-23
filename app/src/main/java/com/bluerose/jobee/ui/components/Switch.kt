package com.bluerose.jobee.ui.components

import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.Checkable
import android.widget.FrameLayout
import androidx.core.content.withStyledAttributes
import com.bluerose.jobee.R
import com.bluerose.jobee.ui.utils.AnimationDuration
import com.bluerose.jobee.ui.utils.Dimensions.dp
import com.bluerose.jobee.ui.utils.getThemeColor

class Switch @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), Checkable {
    private var isCheckedState = false
    private var animationDuration = 0L

    private var onCheckedChanged: ((checked: Boolean) -> Unit)? = null

    private val switchWidth = 44.dp
    private val switchHeight = 24.dp
    private val thumbStrokeWidth = 2.dp
    private val colorPrimary = context.getThemeColor(R.attr.colorPrimary)
    private val colorSurfaceHighest = context.getThemeColor(R.attr.colorSurfaceHighestVariant)
    private val colorWhite = context.getColor(R.color.white)
    private lateinit var backgroundDrawable: GradientDrawable
    private lateinit var thumbBackgroundDrawable: GradientDrawable

    private val thumb = View(context).apply {
        layoutParams = LayoutParams(switchHeight, switchHeight)
    }

    private data class AnimatedStateConfig(
        val backgroundColor: Pair<Int, Int>,
        val thumbTranslateX: Pair<Float, Float>
    )

    init {
        isClickable = true
        isFocusable = true
        addView(thumb)
        context.withStyledAttributes(attrs, R.styleable.Switch, defStyleAttr, 0) {
            isCheckedState = getBoolean(R.styleable.Switch_checked, false)
            animationDuration = getInt(
                R.styleable.TextField_animationDuration,
                AnimationDuration.SHORT.duration
            ).toLong()
        }
        setupView()
        setupListeners()
    }

    private fun setupView() {
        val backgroundColor = if (isCheckedState) colorPrimary else colorSurfaceHighest
        backgroundDrawable = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            setColor(backgroundColor)
            cornerRadius = 100f.dp
        }
        thumbBackgroundDrawable = GradientDrawable().apply {
            shape = GradientDrawable.OVAL
            setColor(colorWhite)
            setStroke(thumbStrokeWidth, backgroundColor)
        }
        background = backgroundDrawable
        thumb.apply {
            background = thumbBackgroundDrawable
            if (isCheckedState) {
                translationX = (switchWidth - switchHeight).toFloat()
            }
        }
    }

    private fun setupListeners() {
        setOnClickListener {
            toggle()
        }
    }

    private fun applyState() {
        onCheckedChanged?.invoke(isCheckedState)
        applyStateAnimation()
    }

    private fun getAnimatedStateConfig(): AnimatedStateConfig {
        return when {
            isCheckedState -> AnimatedStateConfig(
                colorSurfaceHighest to colorPrimary,
                0f to (switchWidth - switchHeight).toFloat()
            )

            else -> AnimatedStateConfig(
                colorPrimary to colorSurfaceHighest,
                (switchWidth - switchHeight).toFloat() to 0f
            )
        }
    }

    private fun applyStateAnimation() {
        val (backgroundColor, thumbTranslateX) = getAnimatedStateConfig()

        val backgroundColorAnimator = ValueAnimator.ofObject(
            ArgbEvaluator(),
            backgroundColor.first,
            backgroundColor.second
        )
        backgroundColorAnimator.addUpdateListener {
            backgroundDrawable.setColor(it.animatedValue as Int)
            thumbBackgroundDrawable.setStroke(thumbStrokeWidth, it.animatedValue as Int)
        }

        val thumbTranslationAnimator = ValueAnimator.ofFloat(thumbTranslateX.first, thumbTranslateX.second)
        thumbTranslationAnimator.addUpdateListener {
            thumb.translationX = it.animatedValue as Float
        }

        val animatorSet = AnimatorSet()
        animatorSet.duration = animationDuration
        animatorSet.playTogether(backgroundColorAnimator, thumbTranslationAnimator)
        animatorSet.start()
    }

    fun setOnCheckedChangedListener(l: (checked: Boolean) -> Unit) {
        onCheckedChanged = l
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(switchWidth, switchHeight)
    }

    override fun setChecked(checked: Boolean) {
        isCheckedState = checked
        applyState()
    }

    override fun isChecked(): Boolean = isCheckedState

    override fun toggle() {
        isCheckedState = !isCheckedState
        applyState()
    }
}