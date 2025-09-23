package com.bluerose.jobee.ui.components

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.withStyledAttributes
import androidx.core.view.children
import com.bluerose.jobee.R
import com.bluerose.jobee.databinding.WidgetListItemBinding

class ListItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.listItemContainerStyle
) : LinearLayout(context, attrs, defStyleAttr) {
    private val binding = WidgetListItemBinding.inflate(LayoutInflater.from(context), this)
    private var text = ""
    private var icon: Drawable? = null
    private var tintColor: Int = 0
    private var isNavigationArrowVisible = true

    init {
        context.withStyledAttributes(attrs, R.styleable.ListItem, defStyleAttr, 0) {
            text = getString(R.styleable.ListItem_text) ?: ""
            icon = getDrawable(R.styleable.ListItem_icon)
            tintColor = getColor(R.styleable.ListItem_tintColor, 0)
            isNavigationArrowVisible = getBoolean(R.styleable.ListItem_navigationArrowVisible, true)
        }
        setupView()
    }

    private fun setupView() {
        binding.title.text = text
        binding.icon.setImageDrawable(icon)
        if (!isNavigationArrowVisible) {
            binding.navigationArrow.visibility = GONE
        }
        if (tintColor != 0) {
            val tintColorStateListValue = ColorStateList.valueOf(tintColor)
            binding.title.setTextColor(tintColor)
            binding.icon.imageTintList = tintColorStateListValue
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (isNavigationArrowVisible && childCount == 4) {
            val view = children.last()
            removeViewAt(3)
            addView(view, 2)
        }
    }
}