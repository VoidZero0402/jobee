package com.bluerose.jobee.ui.components

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import com.bluerose.jobee.R
import com.bluerose.jobee.databinding.LayoutActionBarBinding
import com.bluerose.jobee.ui.utils.Dimensions.dp
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
    val actions: List<ImageButton>
        get() = binding.actionsContainer.children.filterIsInstance<ImageButton>().toList()

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

    fun setActions(vararg actions: Action) {
        actions.forEach {
            binding.actionsContainer.addView(ImageButton(context, null, R.attr.iconButtonStyle).apply {
                layoutParams = LayoutParams(48.dp, 48.dp)
                setImageDrawable(it.icon)
                contentDescription = it.contentDescription
                setOnClickListener(it.onClick)
            })
        }
    }

    fun getActionAtPosition(position: Int): ImageButton? {
        return binding.actionsContainer.children.elementAtOrNull(position) as ImageButton?
    }
}