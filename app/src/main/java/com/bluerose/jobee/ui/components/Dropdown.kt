package com.bluerose.jobee.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Space
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.children
import com.bluerose.jobee.R
import com.bluerose.jobee.ui.utils.Dimensions.dp

class Dropdown @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.dropdownContainerStyle
) : LinearLayout(context, attrs, defStyleAttr) {
    private val labels = arrayListOf<String>()
    private var onOptionClicked: ((position: Int) -> Unit)? = null

    private fun setupOptions() {
        labels.forEachIndexed { index, label ->
            addView(DropdownItem(context).apply {
                text = label
                setOnClickListener {
                    onOptionClicked?.invoke(index)
                }
            })
            if (index != labels.count() - 1) {
                addView(Space(context).apply {
                    layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, 4.dp)
                })
            }
        }

        children.forEach {
            if (it is DropdownItem) {
                it.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            }
        }
        
        measure(
            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        )
    }

    fun setOptions(vararg labels: String) {
        this.labels.addAll(labels)
        removeAllViews()
        setupOptions()
    }

    fun setOnOptionClickedListener(l: (position: Int) -> Unit) {
        onOptionClicked = l
    }

    class DropdownItem @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = R.attr.dropdownItemStyle
    ) : AppCompatTextView(context, attrs, defStyleAttr) {
        init {
            isClickable = true
            isFocusable = true
            layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
    }
}