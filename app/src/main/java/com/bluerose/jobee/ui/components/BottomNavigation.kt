package com.bluerose.jobee.ui.components

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.animation.doOnEnd
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import com.bluerose.jobee.R
import com.bluerose.jobee.ui.utils.AnimationDuration
import com.bluerose.jobee.ui.utils.Dimensions.dp

class BottomNavigation @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.bottomNavigationContainerStyle
) : LinearLayout(context, attrs, defStyleAttr) {
    private var isNavigationHide = false
    private lateinit var activeNavItem: NavItem
    private var onNavItemSelected: ((nav: NavItem, position: Int) -> Unit)? = null

    init {
        applySystemInsets()
    }

    private data class AnimatedStateConfig(
        val translateY: Pair<Float, Float>,
        val alpha: Pair<Float, Float>
    )

    private fun applySystemInsets() {
        val initialPadding = paddingBottom
        ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(v.paddingLeft, v.paddingTop, v.paddingRight, initialPadding + systemBars.bottom)
            insets
        }
        ViewCompat.requestApplyInsets(this)
    }

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
            isNavigationHide -> AnimatedStateConfig(0f to 8f.dp, 1f to 0f)
            else -> AnimatedStateConfig(8f.dp to 0f, 0f to 1f)
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
        animatorSet.duration = AnimationDuration.LONG.duration.toLong()
        animatorSet.playTogether(translationAnimator, alphaAnimator)

        if (isNavigationHide) {
            animatorSet.doOnEnd {
                visibility = GONE
            }
        } else {
            visibility = VISIBLE
        }

        animatorSet.start()
    }

    fun hideAnimated() {
        isNavigationHide = true
        applyStateAnimation()
    }

    fun showAnimated() {
        isNavigationHide = false
        applyStateAnimation()
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

    fun isHidden(): Boolean = isNavigationHide

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