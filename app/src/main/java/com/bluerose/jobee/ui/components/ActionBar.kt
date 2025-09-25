package com.bluerose.jobee.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.bluerose.jobee.R
import com.bluerose.jobee.databinding.LayoutActionBarBinding

class ActionBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.actionBarContainerStyle
) : LinearLayout(context, attrs, defStyleAttr) {
    private val binding = LayoutActionBarBinding.inflate(LayoutInflater.from(context), this)
}