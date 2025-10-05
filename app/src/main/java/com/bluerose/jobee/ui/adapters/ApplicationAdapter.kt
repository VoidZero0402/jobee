package com.bluerose.jobee.ui.adapters

import com.bluerose.jobee.R
import com.bluerose.jobee.abstractions.BaseRecyclerViewAdapter
import com.bluerose.jobee.data.models.Application
import com.bluerose.jobee.databinding.ItemCardApplicationBinding
import com.bluerose.jobee.ui.utils.getThemeColor

class ApplicationAdapter(
    applications: MutableList<Application>,
    private val onApplicationActionListener: OnApplicationActionListener
) : BaseRecyclerViewAdapter<Application, ItemCardApplicationBinding>(applications) {

    data class StageResources(
        val title: Int,
        val background: Int,
        val textColor: Int
    )

    override fun onBindView(binding: ItemCardApplicationBinding, item: Application, position: Int) {
        binding.job.text = item.job.title
        binding.companyName.text = item.job.company.name
        binding.companyLogo.setImageResource(item.job.company.logo)

        val (title, background, textColor) = stageMap[item.stage]!!

        binding.status.apply {
            setText(title)
            setBackgroundResource(background)
            setTextColor(binding.root.context.getThemeColor(textColor))
        }

        binding.root.setOnClickListener {
            onApplicationActionListener.onApplicationClick(item)
        }
    }

    interface OnApplicationActionListener {
        fun onApplicationClick(application: Application)
    }

    companion object {
        private val stageMap = mapOf(
            Application.Stage.SENT to StageResources(
                R.string.application_sent,
                R.drawable.shape_tag_info,
                R.attr.colorInfo
            ),
            Application.Stage.PENDING to StageResources(
                R.string.application_pending,
                R.drawable.shape_tag_warning,
                R.attr.colorWarning
            ),
            Application.Stage.REJECTED to StageResources(
                R.string.application_rejected,
                R.drawable.shape_tag_error,
                R.attr.colorError
            ),
            Application.Stage.ACCEPTED to StageResources(
                R.string.application_accepted,
                R.drawable.shape_tag_success,
                R.attr.colorSuccess
            )
        )
    }
}