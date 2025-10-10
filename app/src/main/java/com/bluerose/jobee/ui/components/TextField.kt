package com.bluerose.jobee.ui.components

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.bluerose.jobee.R
import com.bluerose.jobee.databinding.WidgetTextFieldBinding
import com.bluerose.jobee.ui.utils.AnimationDuration
import com.bluerose.jobee.ui.utils.Dimensions.dp
import com.bluerose.jobee.ui.utils.getThemeColor

class TextField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.textFieldContainerStyle
) : LinearLayout(context, attrs, defStyleAttr) {
    private val binding = WidgetTextFieldBinding.inflate(LayoutInflater.from(context), this)
    private var inputType = 0
    private var hint = ""
    private var defaultText = ""
    private var icon: Drawable? = null
    private var actionType: Int = 0
    private var actionIcon: Drawable? = null
    private var animationDuration = 0L

    private var onFocusChanged: ((hasFocus: Boolean) -> Unit)? = null
    private var onTextChanged: ((text: String) -> Unit)? = null

    private val colorPrimary = context.getThemeColor(R.attr.colorPrimary)
    private val colorPrimaryContainer = context.getThemeColor(R.attr.colorPrimaryContainer)
    private val colorSurfaceHigh = context.getThemeColor(R.attr.colorSurfaceHigh)
    private val colorNeutralLow = context.getThemeColor(R.attr.colorNeutralLow)
    private val colorNeutralHighest = context.getThemeColor(R.attr.colorNeutralHighest)
    private val strokeWidth = 1.dp
    private val backgroundDrawable = GradientDrawable().apply {
        shape = GradientDrawable.RECTANGLE
        setColor(colorSurfaceHigh)
        cornerRadius = 16f.dp
        setStroke(strokeWidth, Color.TRANSPARENT)
    }

    private var currentState = TextFieldState.EMPTY

    private data class AnimatedStateConfig(
        val backgroundColor: Pair<Int, Int>,
        val strokeColor: Pair<Int, Int>,
        val tintColor: Int,
    )

    private enum class TextFieldState {
        EMPTY,
        FOCUSED,
        FILLED
    }

    init {
        background = backgroundDrawable
        context.withStyledAttributes(attrs, R.styleable.TextField, defStyleAttr, 0) {
            inputType = getInt(R.styleable.TextField_inputType, 0)
            hint = getString(R.styleable.TextField_hint) ?: ""
            defaultText = getString(R.styleable.TextField_text) ?: ""
            icon = getDrawable(R.styleable.TextField_icon)
            actionType = getInt(R.styleable.TextField_action, 0)
            actionIcon = getDrawable(R.styleable.TextField_actionIcon)
            animationDuration = getInt(
                R.styleable.TextField_animationDuration,
                AnimationDuration.MEDIUM.duration
            ).toLong()
        }
        setupView()
        setupListeners()
    }

    private fun setupView() {
        binding.editText.apply {
            inputType = when (this@TextField.inputType) {
                0 -> InputType.TYPE_CLASS_TEXT
                1 -> InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                2 -> InputType.TYPE_CLASS_PHONE
                3 -> InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                else -> InputType.TYPE_CLASS_TEXT
            }
            hint = this@TextField.hint
            if (defaultText.isNotEmpty()) {
                setText(defaultText)
                applyFilledState()
                currentState = TextFieldState.FILLED
            }
        }
        if (icon != null) {
            binding.icon.apply {
                setImageDrawable(icon)
                visibility = VISIBLE
            }
        }
        if (actionType != 0) {
            binding.action.apply {
                setImageDrawable(
                    when (actionType) {
                        1 -> ContextCompat.getDrawable(context, R.drawable.ic_eye_bold)
                        2 -> ContextCompat.getDrawable(context, R.drawable.ic_close_bold)
                        3 -> actionIcon
                        else -> null
                    }
                )
                visibility = VISIBLE
                when (actionType) {
                    1 -> setOnClickListener { togglePasswordVisibility() }
                    2 -> setOnClickListener { clearText() }
                }
            }
        }
    }

    private fun setupListeners() {
        binding.editText.setOnFocusChangeListener { _, hasFocus ->
            applyState(
                when {
                    hasFocus -> TextFieldState.FOCUSED
                    binding.editText.text.isNotEmpty() -> TextFieldState.FILLED
                    else -> TextFieldState.EMPTY
                }
            )
            onFocusChanged?.invoke(hasFocus)
        }
        binding.editText.addTextChangedListener {
            when {
                it.toString().isEmpty() && currentState == TextFieldState.FILLED -> applyState(TextFieldState.EMPTY)
                it.toString().isNotEmpty() && currentState == TextFieldState.EMPTY -> applyState(TextFieldState.FILLED)
            }
            onTextChanged?.invoke(it.toString())
        }
    }

    private fun applyState(state: TextFieldState) {
        applyStateAnimation(state)
        currentState = state
    }

    private fun getAnimatedStateConfig(state: TextFieldState): AnimatedStateConfig {
        return when {
            currentState == TextFieldState.FILLED && state == TextFieldState.EMPTY -> AnimatedStateConfig(
                colorSurfaceHigh to colorSurfaceHigh,
                Color.TRANSPARENT to Color.TRANSPARENT,
                colorNeutralLow
            )

            currentState == TextFieldState.EMPTY && state == TextFieldState.FILLED -> AnimatedStateConfig(
                colorSurfaceHigh to colorSurfaceHigh,
                Color.TRANSPARENT to Color.TRANSPARENT,
                colorNeutralHighest
            )

            else -> when (state) {
                TextFieldState.EMPTY -> AnimatedStateConfig(
                    colorPrimaryContainer to colorSurfaceHigh,
                    colorPrimary to Color.TRANSPARENT,
                    colorNeutralLow
                )

                TextFieldState.FOCUSED -> AnimatedStateConfig(
                    colorSurfaceHigh to colorPrimaryContainer,
                    Color.TRANSPARENT to colorPrimary,
                    colorPrimary
                )

                TextFieldState.FILLED -> AnimatedStateConfig(
                    colorPrimaryContainer to colorSurfaceHigh,
                    colorPrimary to Color.TRANSPARENT,
                    colorNeutralHighest
                )
            }
        }
    }

    private fun applyStateAnimation(state: TextFieldState) {
        val animators = arrayListOf<Animator>()
        val (backgroundColor, strokeColor, tintColor) = getAnimatedStateConfig(state)

        val backgroundColorAnimator = ValueAnimator.ofObject(
            ArgbEvaluator(),
            backgroundColor.first,
            backgroundColor.second
        )
        backgroundColorAnimator.addUpdateListener {
            backgroundDrawable.setColor(it.animatedValue as Int)
        }
        animators.add(backgroundColorAnimator)

        val backgroundStrokeAnimator = ValueAnimator.ofObject(ArgbEvaluator(), strokeColor.first, strokeColor.second)
        backgroundStrokeAnimator.addUpdateListener {
            backgroundDrawable.setStroke(strokeWidth, it.animatedValue as Int)
        }
        animators.add(backgroundStrokeAnimator)

        if (binding.icon.isVisible) {
            val iconTintAnimator = ValueAnimator.ofObject(
                ArgbEvaluator(),
                binding.icon.imageTintList?.defaultColor,
                tintColor
            )
            iconTintAnimator.addUpdateListener {
                binding.icon.imageTintList = ColorStateList.valueOf(it.animatedValue as Int)
            }
            animators.add(iconTintAnimator)
        }

        if (binding.action.isVisible) {
            val actionTintAnimator = ValueAnimator.ofObject(
                ArgbEvaluator(),
                binding.action.imageTintList?.defaultColor,
                tintColor
            )
            actionTintAnimator.addUpdateListener {
                binding.action.imageTintList = ColorStateList.valueOf(it.animatedValue as Int)
            }
            animators.add(actionTintAnimator)
        }

        val animatorSet = AnimatorSet()
        animatorSet.duration = animationDuration
        animatorSet.playTogether(*animators.toTypedArray())
        animatorSet.start()
    }

    private fun applyFilledState() {
        binding.icon.imageTintList = ColorStateList.valueOf(colorNeutralHighest)
        binding.action.imageTintList = ColorStateList.valueOf(colorNeutralHighest)
    }

    private fun togglePasswordVisibility() {
        with(binding.editText) {
            inputType = if (inputType == (InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            setSelection(text.length)
        }
    }

    private fun clearText() {
        binding.editText.setText("")
    }

    fun setActionOnClickListener(l: OnClickListener) {
        binding.action.setOnClickListener(l)
    }

    fun setOnFocusChangedListener(l: (hasFocus: Boolean) -> Unit) {
        onFocusChanged = l
    }

    fun setOnTextChangedListener(l: (text: String) -> Unit) {
        onTextChanged = l
    }

    fun getText(): String = binding.editText.text.toString()

    fun setText(text: String) {
        binding.editText.setText(text)
    }
}