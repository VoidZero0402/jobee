package com.bluerose.jobee.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.bluerose.jobee.R
import com.bluerose.jobee.data.models.Job
import com.bluerose.jobee.databinding.WidgetJobDetailsBinding

class JobDetails @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.jobDetailsContainerStyle
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding = WidgetJobDetailsBinding.inflate(LayoutInflater.from(context), this)

    fun setJob(job: Job) {}
}