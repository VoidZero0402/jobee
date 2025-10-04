package com.bluerose.jobee.ui.adapters

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.View
import android.widget.TextView
import com.bluerose.jobee.R
import com.bluerose.jobee.abstractions.BaseRecyclerViewAdapter
import com.bluerose.jobee.data.models.Job
import com.bluerose.jobee.databinding.ItemCardJobBinding
import com.bluerose.jobee.ui.utils.getThemeColor

class JobAdapter(
    jobs: MutableList<Job>,
    private val onJobActionListener: OnJobActionListener
) : BaseRecyclerViewAdapter<Job, ItemCardJobBinding>(jobs) {

    override fun onBindView(binding: ItemCardJobBinding, item: Job, position: Int) {
        binding.title.text = item.title
        binding.companyName.text = item.company.name
        binding.companyLogo.setImageResource(item.company.logo)
        binding.location.text = item.location
        binding.salary.text = binding.root.context.getString(
            R.string.job_salary_format,
            item.salaryRange.first,
            item.salaryRange.second
        )

        if (item.isSaved) setSavedState(binding)

        val tagsFlowIndex = binding.root.indexOfChild(binding.tagsFlow)
        binding.root.removeViews(tagsFlowIndex + 1, binding.root.childCount - (tagsFlowIndex + 1))

        val tagIds = IntArray(item.tags.size)
        item.tags.forEachIndexed { index, label ->
            val tag = TextView(binding.root.context, null, R.attr.tagStyle).apply {
                id = View.generateViewId()
                text = label
            }
            tagIds[index] = tag.id
            binding.root.addView(tag)
        }
        binding.tagsFlow.referencedIds = tagIds

        binding.root.setOnClickListener {
            onJobActionListener.onJobClick(item)
        }
        binding.saveAction.setOnClickListener {
            item.isSaved = !item.isSaved
            if (item.isSaved) {
                setSavedState(binding)
                onJobActionListener.onJobSaved(item)
            } else {
                setUnsavedState(binding)
                onJobActionListener.onJobUnsaved(item)
            }
        }
    }

    private fun setSavedState(binding: ItemCardJobBinding) {
        binding.saveAction.apply {
            setImageResource(R.drawable.ic_bookmark_bold)
            imageTintList = ColorStateList.valueOf(
                binding.root.context.getThemeColor(R.attr.colorPrimary)
            )
        }
    }

    private fun setUnsavedState(binding: ItemCardJobBinding) {
        binding.saveAction.apply {
            setImageResource(R.drawable.ic_bookmark)
            imageTintList = ColorStateList.valueOf(
                binding.root.context.getThemeColor(R.attr.colorNeutralHighest)
            )
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setJobs(jobs: MutableList<Job>) {
        data = jobs
        notifyDataSetChanged()
    }

    fun removeJob(job: Job) {
        val position = data.indexOfFirst { it.id === job.id }
        if (position != -1) {
            data.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    interface OnJobActionListener {
        fun onJobClick(job: Job)
        fun onJobSaved(job: Job)
        fun onJobUnsaved(job: Job)
    }
}