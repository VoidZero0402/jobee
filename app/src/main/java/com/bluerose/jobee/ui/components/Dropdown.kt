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
    private val options = arrayListOf<Option>()
    private var onOptionClicked: ((option: Option) -> Unit)? = null

    data class Option(
        val id: String,
        val label: String
    )

    private fun setupOptions() {
        options.forEachIndexed { index, option ->
            addView(DropdownItem(context).apply {
                text = option.label
                setOnClickListener {
                    onOptionClicked?.invoke(option)
                }
            })
            if (index != options.count() - 1) {
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
    }

    fun setOptions(options: List<Option>) {
        this.options.addAll(options)
        removeAllViews()
        setupOptions()
    }

    fun setOnOptionClickedListener(l: (option: Option) -> Unit) {
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