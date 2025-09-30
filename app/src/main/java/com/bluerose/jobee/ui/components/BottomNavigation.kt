package com.bluerose.jobee.ui.components

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.animation.doOnEnd
import androidx.core.view.children
import com.bluerose.jobee.R
import com.bluerose.jobee.ui.utils.AnimationDuration
import com.bluerose.jobee.ui.utils.Dimensions.dp
import com.bluerose.jobee.ui.utils.applySystemBottomInsets

class BottomNavigation @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.bottomNavigationContainerStyle
) : LinearLayout(context, attrs, defStyleAttr) {
    private var isNavigationHide = false
    val isHidden get() = isNavigationHide
    private lateinit var activeNavItem: NavItem
    private var onNavItemSelected: ((nav: NavItem, position: Int) -> Unit)? = null

    init {
        applySystemBottomInsets()
    }

    private data class AnimatedStateConfig(
        val translateY: Pair<Float, Float>,
        val alpha: Pair<Float, Float>
    )

    private fun selectNavItem(nav: NavItem, position: Int) {
        if (nav != activeNavItem) {
            activeNavItem.deactivate()
            activeNavItem = nav
            activeNavItem.activate()
            onNavItemSelected?.invoke(activeNavItem, position)
        }
    }

    private fun getAnimatedStateConfig(): AnimatedStateConfig {
        return when {
            isNavigationHide -> AnimatedStateConfig(0f to 4f.dp, 1f to 0f)
            else -> AnimatedStateConfig(4f.dp to 0f, 0f to 1f)
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

        if (isNavigationHide) {
            animatorSet.doOnEnd {
                visibility = INVISIBLE
            }
        } else {
            visibility = VISIBLE
        }

        animatorSet.start()
    }

    fun hide() {
        if (!isNavigationHide) {
            isNavigationHide = true
            visibility = INVISIBLE
        }
    }

    fun hideAnimated() {
        if (!isNavigationHide) {
            isNavigationHide = true
            applyStateAnimation()
        }
    }

    fun show() {
        if (isNavigationHide) {
            isNavigationHide = false
            visibility = VISIBLE
        }
    }

    fun showAnimated() {
        if (isNavigationHide) {
            isNavigationHide = false
            applyStateAnimation()
        }
    }

    fun selectNavItemAtPosition(position: Int) {
        val nav = children.elementAtOrNull(position) as NavItem?
        if (nav != null) {
            selectNavItem(nav, position)
        }
    }

    fun setOnNavItemSelectedListener(l: (nav: NavItem, position: Int) -> Unit) {
        onNavItemSelected = l
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        activeNavItem = children.first() as NavItem
        activeNavItem.activate()
        children.forEachIndexed { index, nav ->
            nav.setOnClickListener {
                selectNavItem(it as NavItem, index)
            }
        }
    }
}