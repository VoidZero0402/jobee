package com.bluerose.jobee.ui.features.jobs

import com.bluerose.jobee.abstractions.BaseFragment
import com.bluerose.jobee.abstractions.LayoutMode
import com.bluerose.jobee.abstractions.LayoutState
import com.bluerose.jobee.databinding.FragmentDiscoverJobsBinding
import com.bluerose.jobee.ui.constants.NavItemPositions

class DiscoverJobsFragment : BaseFragment<FragmentDiscoverJobsBinding>() {
    override val layoutState = LayoutState(
        LayoutMode.BOTTOM_NAV,
        selectedNavItemPosition = NavItemPositions.DISCOVER_JOBS
    )
}