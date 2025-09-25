package com.bluerose.jobee.ui.components

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.withStyledAttributes
import com.bluerose.jobee.R
import com.bluerose.jobee.databinding.WidgetNavItemBinding
import com.bluerose.jobee.ui.utils.getThemeColor

class NavItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.navItemContainerStyle
) : LinearLayout(context, attrs, defStyleAttr) {
    private val binding = WidgetNavItemBinding.inflate(LayoutInflater.from(context), this)
    private var isActiveState: Boolean = false
    private var text = ""
    private var icon: Drawable? = null
    private var activeIcon: Drawable? = null

    private val colorPrimary = context.getThemeColor(R.attr.colorPrimary)
    private val colorNeutralLow = context.getThemeColor(R.attr.colorNeutralLow)

    private data class StateConfig(
        val color: Int,
        val icon: Drawable?
    )

    init {
        context.withStyledAttributes(attrs, R.styleable.NavItem, defStyleAttr, 0) {
            text = getString(R.styleable.NavItem_text) ?: ""
            icon = getDrawable(R.styleable.NavItem_icon)
            activeIcon = getDrawable(R.styleable.NavItem_activeIcon)
        }
        setupView()
    }

    private fun setupView() {
        binding.label.text = text
        binding.icon.setImageDrawable(icon)
    }

    private fun getStateConfig(): StateConfig {
        return when {
            isActiveState -> StateConfig(colorPrimary, activeIcon)
            else -> StateConfig(colorNeutralLow, icon)
        }
    }

    private fun applyStateInstant() {
        val (color, icon) = getStateConfig()
        binding.label.setTextColor(color)
        binding.icon.apply {
            setImageDrawable(icon)
            imageTintList = ColorStateList.valueOf(color)
        }
    }

    fun activate() {
        isActiveState = true
        applyStateInstant()
    }

    fun deactivate() {
        isActiveState = false
        applyStateInstant()
    }
}