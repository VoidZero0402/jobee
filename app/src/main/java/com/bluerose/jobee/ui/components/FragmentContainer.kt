package com.bluerose.jobee.ui.components

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import com.bluerose.jobee.R
import com.bluerose.jobee.abstractions.LayoutMode
import com.bluerose.jobee.ui.utils.applySystemInsets

class FragmentContainer @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private var initialPaddingTop = 0
    private var initialPaddingBottom = 0
    private var shouldApplyTopInset = true
    private var shouldApplyBottomInset = true

    init {
        initialPaddingTop = paddingTop
        initialPaddingBottom = paddingBottom
        applySystemInsets {
            setPadding(
                paddingStart,
                if (shouldApplyTopInset) initialPaddingTop + it.top else initialPaddingTop,
                paddingEnd,
                if (shouldApplyBottomInset) initialPaddingBottom + it.bottom else initialPaddingBottom
            )
        }
    }

    private data class LayoutConstraints(
        val topToTop: Int = ConstraintLayout.LayoutParams.UNSET,
        val topToBottom: Int = ConstraintLayout.LayoutParams.UNSET,
        val bottomToTop: Int = ConstraintLayout.LayoutParams.UNSET,
        val bottomToBottom: Int = ConstraintLayout.LayoutParams.UNSET
    )

    fun applyLayoutMode(layoutMode: LayoutMode) {
        shouldApplyTopInset = layoutMode == LayoutMode.FULL_SCREEN || layoutMode == LayoutMode.BOTTOM_NAV
        shouldApplyBottomInset = layoutMode == LayoutMode.FULL_SCREEN || layoutMode == LayoutMode.ACTION_BAR

        val constraints = when (layoutMode) {
            LayoutMode.FULL_SCREEN -> LayoutConstraints(
                topToTop = ConstraintLayout.LayoutParams.PARENT_ID,
                bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
            )

            LayoutMode.ACTION_BAR -> LayoutConstraints(
                topToBottom = R.id.action_bar,
                bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
            )

            LayoutMode.BOTTOM_NAV -> LayoutConstraints(
                topToTop = ConstraintLayout.LayoutParams.PARENT_ID,
                bottomToTop = R.id.bottom_navigation
            )

            LayoutMode.ACTION_BAR_AND_BOTTOM_NAV -> LayoutConstraints(
                topToBottom = R.id.action_bar,
                bottomToTop = R.id.bottom_navigation
            )
        }

        updateLayoutParams<ConstraintLayout.LayoutParams> {
            topToTop = constraints.topToTop
            topToBottom = constraints.topToBottom
            bottomToTop = constraints.bottomToTop
            bottomToBottom = constraints.bottomToBottom
        }
    }
}