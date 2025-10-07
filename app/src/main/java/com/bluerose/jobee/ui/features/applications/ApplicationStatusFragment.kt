package com.bluerose.jobee.ui.features.applications

import android.os.Bundle
import android.view.View
import com.bluerose.jobee.R
import com.bluerose.jobee.abstractions.BaseFragment
import com.bluerose.jobee.abstractions.LayoutMode
import com.bluerose.jobee.abstractions.LayoutState
import com.bluerose.jobee.data.models.Application
import com.bluerose.jobee.databinding.FragmentApplicationStatusBinding
import com.bluerose.jobee.ui.components.ActionBar
import com.bluerose.jobee.ui.constants.BundleKeys
import com.bluerose.jobee.ui.features.home.HomeFragment
import com.bluerose.jobee.ui.utils.getThemeColor
import com.bluerose.jobee.ui.utils.parcelable

class ApplicationStatusFragment : BaseFragment<FragmentApplicationStatusBinding>() {
    override val layoutState = LayoutState(
        LayoutMode.ACTION_BAR,
        lazy { ActionBar.Config(resources.getString(R.string.action_bar_application_status)) }
    )

    data class StageResources(
        val title: Int,
        val background: Int,
        val textColor: Int
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val application = arguments?.parcelable<Application>(BundleKeys.APPLICATION)

        if (application != null) {
            binding.jobDetails.setJob(application.job)

            val (title, background, textColor) = stageMap[application.stage]!!
            binding.status.apply {
                setText(title)
                setBackgroundResource(background)
                setTextColor(binding.root.context.getThemeColor(textColor))
            }
        } else {
            navigateTo(HomeFragment())
        }
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