package com.bluerose.jobee.ui.components

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bluerose.jobee.R
import com.bluerose.jobee.databinding.LayoutActionBarBinding
import com.bluerose.jobee.ui.utils.preformBackNavigation

class ActionBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.actionBarContainerStyle
) : LinearLayout(context, attrs, defStyleAttr) {
    private val binding = LayoutActionBarBinding.inflate(LayoutInflater.from(context), this)
    private var _title = resources.getString(R.string.app_name)
    var title: String
        get() = _title
        set(value) {
            _title = value
            binding.title.text = _title
        }

    data class Action(
        val icon: Drawable?,
        val contentDescription: String,
        val onClick: OnClickListener?
    )

    init {
        applySystemInsets()
        setupView()
        setupListeners()
    }

    private fun applySystemInsets() {
        val initialPadding = paddingTop
        ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(v.paddingLeft, initialPadding + systemBars.top, v.paddingRight, paddingBottom)
            insets
        }
        ViewCompat.requestApplyInsets(this)
    }

    private fun setupView() {
        binding.title.text = _title
    }

    private fun setupListeners() {
        binding.navigationAction.setOnClickListener {
            context.preformBackNavigation()
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
        binding.navigationAction.visibility = VISIBLE
    }

    fun hideNavigationAction() {
        binding.navigationAction.visibility = GONE
    }

    fun setOnNavigationActionClickListener(l: OnClickListener) {
        binding.navigationAction.setOnClickListener(l)
    }
}