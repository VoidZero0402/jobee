package com.bluerose.jobee.ui.features.jobs

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import com.bluerose.jobee.R
import com.bluerose.jobee.abstractions.BaseFragment
import com.bluerose.jobee.abstractions.LayoutMode
import com.bluerose.jobee.abstractions.LayoutState
import com.bluerose.jobee.data.models.Job
import com.bluerose.jobee.databinding.FragmentJobDetailsBinding
import com.bluerose.jobee.di.Singletons
import com.bluerose.jobee.ui.components.ActionBar
import com.bluerose.jobee.ui.constants.BundleKeys
import com.bluerose.jobee.ui.features.home.HomeFragment
import com.bluerose.jobee.ui.utils.getThemeColor
import com.bluerose.jobee.ui.utils.parcelable

class JobDetailsFragment : BaseFragment<FragmentJobDetailsBinding>() {
    override val layoutState = LayoutState(
        LayoutMode.ACTION_BAR,
        lazy {
            ActionBar.Config(
                actions = listOf(
                    ActionBar.Action(
                        ContextCompat.getDrawable(requireContext(), R.drawable.ic_bookmark),
                        resources.getString(R.string.cd_save_job),
                        this::onSaveJob
                    ),
                    ActionBar.Action(
                        ContextCompat.getDrawable(requireContext(), R.drawable.ic_share),
                        resources.getString(R.string.cd_share),
                        null
                    )
                )
            )
        }
    )
    private lateinit var job: Job

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundleJob = arguments?.parcelable<Job>(BundleKeys.JOB)

        if (bundleJob != null) {
            job = bundleJob
            binding.jobDetails.setJob(job)
            if (job.isSaved) {
                setSavedState(supportActionBar!!.actions[0])
            }
        } else {
            navigateTo(HomeFragment())
        }
    }

    private fun onSaveJob(action: View) {
        job.isSaved = !job.isSaved

        if (job.isSaved) {
            Singletons.repository.saveJob(job.id)
            setSavedState(action as ImageButton)
        } else {
            Singletons.repository.unsaveJob(job.id)
            setUnsavedState(action as ImageButton)
        }
    }

    private fun setSavedState(action: ImageButton) {
        action.apply {
            setImageResource(R.drawable.ic_bookmark_bold)
            imageTintList = ColorStateList.valueOf(context.getThemeColor(R.attr.colorPrimary))
            contentDescription = resources.getString(R.string.cd_unsave_job)
        }
    }

    private fun setUnsavedState(action: ImageButton) {
        action.apply {
            setImageResource(R.drawable.ic_bookmark)
            imageTintList = ColorStateList.valueOf(context.getThemeColor(R.attr.colorNeutralHighest))
            contentDescription = resources.getString(R.string.cd_save_job)
        }
    }
}