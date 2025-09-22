package com.bluerose.jobee.ui.components

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.helper.widget.Flow
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.withStyledAttributes
import com.bluerose.jobee.R
import com.bluerose.jobee.ui.utils.Dimensions.dp

class ChipGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val flow = Flow(context).apply {
        id = generateViewId()
        layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT
        )
        setHorizontalStyle(Flow.CHAIN_PACKED)
        setVerticalStyle(Flow.CHAIN_PACKED)
        setHorizontalBias(0f)
        setVerticalBias(0f)
    }

    init {
        addView(flow)
        context.withStyledAttributes(attrs, R.styleable.ChipGroup, defStyleAttr, 0) {
            flow.apply {
                setWrapMode(getInt(R.styleable.ChipGroup_wrapMode, Flow.WRAP_NONE))
                setHorizontalGap(getDimensionPixelSize(R.styleable.ChipGroup_horizontalGap, 8.dp))
                setVerticalGap(getDimensionPixelSize(R.styleable.ChipGroup_verticalGap, 8.dp))
            }
        }
    }
}