package com.bluerose.jobee.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bluerose.jobee.R
import com.bluerose.jobee.data.models.Job
import com.bluerose.jobee.databinding.WidgetJobDetailsBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class JobDetails @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.jobDetailsContainerStyle
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding = WidgetJobDetailsBinding.inflate(LayoutInflater.from(context), this)

    fun setJob(job: Job) {
        binding.title.text = job.title
        binding.companyName.text = job.company.name
        binding.companyLogo.setImageResource(job.company.logo)
        binding.location.text = job.location
        binding.salary.text = context.getString(
            R.string.job_salary_format,
            job.salaryRange.first,
            job.salaryRange.second
        )

        val date = Date(job.createdAt)
        binding.postedAt.text = context.getString(
            R.string.job_pasted_at_format,
            SimpleDateFormat("dd MMM, yyyy – HH:mm a", Locale.ENGLISH).format(date)
        )

        val tagIds = IntArray(job.tags.size)
        job.tags.forEachIndexed { index, label ->
            val tag = TextView(binding.root.context, null, R.attr.tagStyle).apply {
                id = View.generateViewId()
                text = label
            }
            tagIds[index] = tag.id
            addView(tag)
        }
        binding.tagsFlow.referencedIds = tagIds
    }
}