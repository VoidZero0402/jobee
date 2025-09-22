package com.bluerose.jobee.ui.components

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.helper.widget.Flow
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.withStyledAttributes
import androidx.core.view.children
import com.bluerose.jobee.R
import com.bluerose.jobee.ui.utils.Dimensions.dp

class ChipGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val flow = Flow(context).apply {
        layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        )
        setHorizontalStyle(Flow.CHAIN_PACKED)
        setVerticalStyle(Flow.CHAIN_PACKED)
        setHorizontalBias(0f)
        setVerticalBias(0f)
    }
    private var isSingleSelection = false
    private val chips: ArrayList<Chip> = arrayListOf()
    private var onSelectionChangedListener: ((selectedChips: List<Chip>) -> Unit)? = null

    init {
        addView(flow)
        context.withStyledAttributes(attrs, R.styleable.ChipGroup, defStyleAttr, 0) {
            flow.apply {
                setWrapMode(getInt(R.styleable.ChipGroup_wrapMode, Flow.WRAP_NONE))
                setHorizontalGap(getDimensionPixelSize(R.styleable.ChipGroup_horizontalGap, 8.dp))
                setVerticalGap(getDimensionPixelSize(R.styleable.ChipGroup_verticalGap, 8.dp))
            }
            isSingleSelection = getBoolean(R.styleable.ChipGroup_singleSelection, false)
        }
    }

    private fun updateFlowReferences() {
        val referencedIds = chips.map { it.id }.toIntArray()
        flow.referencedIds = referencedIds
    }

    private fun handleSelection(chipId: Int, isChecked: Boolean) {
        if (isSingleSelection && isChecked) {
            chips.find { it.id != chipId && it.isChecked }?.toggle()
        }
        onSelectionChangedListener?.invoke(getSelectedChips())
    }

    private fun getSelectedChips(): List<Chip> {
        return chips.filter { it.isChecked }
    }

    fun setOnSelectionChangedListener(l: (selectedChips: List<Chip>) -> Unit) {
        onSelectionChangedListener = l
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        children.filterIsInstance<Chip>().forEach { chip ->
            chips.add(chip)
            chip.setOnCheckedChangedListener {
                handleSelection(chip.id, it)
            }
        }

        updateFlowReferences()
    }
}