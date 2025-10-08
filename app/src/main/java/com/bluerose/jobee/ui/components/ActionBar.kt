package com.bluerose.jobee.ui.components

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.core.animation.doOnEnd
import androidx.core.view.children
import com.bluerose.jobee.R
import com.bluerose.jobee.databinding.LayoutActionBarBinding
import com.bluerose.jobee.ui.utils.AnimationDuration
import com.bluerose.jobee.ui.utils.Dimensions.dp
import com.bluerose.jobee.ui.utils.applySystemTopInsets
import com.bluerose.jobee.ui.utils.preformBackNavigation

class ActionBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.actionBarContainerStyle
) : LinearLayout(context, attrs, defStyleAttr) {
    private val binding = LayoutActionBarBinding.inflate(LayoutInflater.from(context), this)
    private var isActionBarHide = false
    val isHidden get() = isActionBarHide
    private var _title = resources.getString(R.string.app_name)
    var title: String
        get() = _title
        set(value) {
            if (value != _title) {
                _title = value
                binding.title.text = _title
            }
        }
    val actions: List<ImageButton>
        get() = binding.actionsContainer.children.filterIsInstance<ImageButton>().toList()

    data class Action(
        val icon: Drawable?,
        val contentDescription: String,
        val onClick: OnClickListener?
    )

    data class Config(
        val title: String = "",
        val isNavigationActionVisible: Boolean = true,
        val navigationAction: Action? = null,
        val shouldResetNavigationAction: Boolean = false,
        val logo: Drawable? = null,
        val actions: List<Action> = emptyList()
    )

    private data class AnimatedStateConfig(
        val translateY: Pair<Float, Float>,
        val alpha: Pair<Float, Float>
    )

    init {
        applySystemTopInsets()
        setupView()
        setupListeners()
    }

    private fun setupView() {
        binding.title.text = _title
    }

    private fun setupListeners() {
        binding.navigationAction.setOnClickListener {
            context.preformBackNavigation()
        }
    }

    private fun getAnimatedStateConfig(): AnimatedStateConfig {
        return when {
            isActionBarHide -> AnimatedStateConfig(0f to (-4f).dp, 1f to 0f)
            else -> AnimatedStateConfig((-4f).dp to 0f, 0f to 1f)
        }
    }

    private fun applyStateAnimation() {
        val (translateYState, alphaState) = getAnimatedStateConfig()
        val translationAnimator = ValueAnimator.ofFloat(translateYState.first, translateYState.second)
        translationAnimator.addUpdateListener {
            translationY = it.animatedValue as Float
        }

        val alphaAnimator = ValueAnimator.ofFloat(alphaState.first, alphaState.second)
        alphaAnimator.addUpdateListener {
            alpha = it.animatedValue as Float
        }

        val animatorSet = AnimatorSet()
        animatorSet.duration = AnimationDuration.MEDIUM.duration.toLong()
        animatorSet.playTogether(translationAnimator, alphaAnimator)

        if (isActionBarHide) {
            animatorSet.doOnEnd {
                visibility = INVISIBLE
            }
        } else {
            visibility = VISIBLE
        }

        animatorSet.start()
    }

    fun applyConfig(config: Config) {
        val (title, isNavigationActionVisible, navigationAction, shouldResetNavigationAction, logo, actions) = config
        this.title = title
        if (isNavigationActionVisible) showNavigationAction() else hideNavigationAction()
        if (navigationAction != null) setNavigationAction(navigationAction)
        if (shouldResetNavigationAction) resetNavigationAction()
        setLogo(logo)
        setActions(actions)
    }

    fun hide() {
        if (!isActionBarHide) {
            isActionBarHide = true
            visibility = INVISIBLE
        }
    }

    fun hideAnimated() {
        if (!isActionBarHide) {
            isActionBarHide = true
            applyStateAnimation()
        }
    }

    fun show() {
        if (isActionBarHide) {
            isActionBarHide = false
            visibility = VISIBLE
        }
    }

    fun showAnimated() {
        if (isActionBarHide) {
            isActionBarHide = false
            applyStateAnimation()
        }
    }

    fun resetNavigationAction() {
        binding.navigationAction.apply {
            setImageResource(R.drawable.ic_arrow_left)
            resources.getString(R.string.cd_back_navigation)
            setOnClickListener {
                context.preformBackNavigation()
            }
        }
    }

    fun setNavigationAction(action: Action) {
        binding.navigationAction.apply {
            setImageDrawable(action.icon)
            contentDescription = action.contentDescription
            setOnClickListener(action.onClick)
        }
    }

    fun setLogo(drawable: Drawable?) {
        binding.logoContainer.visibility = if (drawable != null) VISIBLE else GONE
        binding.logo.setImageDrawable(drawable)
    }

    fun showNavigationAction() {
        binding.navigationAction.apply {
            if (visibility != VISIBLE) {
                visibility = VISIBLE
            }
        }
    }

    fun hideNavigationAction() {
        binding.navigationAction.apply {
            if (visibility != GONE) {
                visibility = GONE
            }
        }
    }

    fun setActions(actions: List<Action>) {
        binding.actionsContainer.removeAllViews()
        actions.forEach {
            binding.actionsContainer.addView(ImageButton(context, null, R.attr.iconButtonStyle).apply {
                layoutParams = LayoutParams(40.dp, 40.dp)
                setImageDrawable(it.icon)
                contentDescription = it.contentDescription
                setOnClickListener(it.onClick)
            })
        }
    }

    fun getActionAtPosition(position: Int): ImageButton? {
        return binding.actionsContainer.children.elementAtOrNull(position) as ImageButton?
    }
}