package com.bluerose.jobee.ui.features.savedjobs

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.bluerose.jobee.R
import com.bluerose.jobee.abstractions.BaseFragment
import com.bluerose.jobee.abstractions.LayoutMode
import com.bluerose.jobee.abstractions.LayoutState
import com.bluerose.jobee.databinding.FragmentSavedJobsBinding
import com.bluerose.jobee.ui.components.ActionBar
import com.bluerose.jobee.ui.constants.NavItemPositions

class SavedJobsFragment : BaseFragment<FragmentSavedJobsBinding>() {
    override val layoutState = LayoutState(
        LayoutMode.ACTION_BAR_AND_BOTTOM_NAV,
        lazy {
            ActionBar.Config(
                title = resources.getString(R.string.action_bar_saved_jobs),
                isNavigationActionVisible = false,
                logo = ContextCompat.getDrawable(requireContext(), R.drawable.logo_jobee)
            )
        },
        NavItemPositions.SAVED_JOBS
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}