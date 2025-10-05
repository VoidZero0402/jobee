package com.bluerose.jobee.ui.features.applications

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bluerose.jobee.R
import com.bluerose.jobee.abstractions.BaseFragment
import com.bluerose.jobee.abstractions.LayoutMode
import com.bluerose.jobee.abstractions.LayoutState
import com.bluerose.jobee.data.models.Application
import com.bluerose.jobee.databinding.FragmentApplicationsBinding
import com.bluerose.jobee.di.Singletons
import com.bluerose.jobee.ui.adapters.ApplicationAdapter
import com.bluerose.jobee.ui.components.ActionBar
import com.bluerose.jobee.ui.constants.NavItemPositions
import com.bluerose.jobee.ui.utils.DrawableItemDecoration

class ApplicationsFragment : BaseFragment<FragmentApplicationsBinding>() {
    override val layoutState = LayoutState(
        LayoutMode.ACTION_BAR_AND_BOTTOM_NAV,
        lazy {
            ActionBar.Config(
                title = resources.getString(R.string.action_bar_applications),
                isNavigationActionVisible = false,
                logo = ContextCompat.getDrawable(requireContext(), R.drawable.logo_jobee),
                actions = listOf(
                    ActionBar.Action(
                        ContextCompat.getDrawable(requireContext(), R.drawable.ic_magnifier),
                        resources.getString(R.string.cd_search),
                        null
                    )
                )
            )
        },
        NavItemPositions.APPLICATIONS
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val onApplicationActionListener = object : ApplicationAdapter.OnApplicationActionListener {
            override fun onApplicationClick(application: Application) {}
        }

        binding.applicationsRecycler.apply {
            adapter = ApplicationAdapter(
                Singletons.repository.getApplications().toMutableList(),
                onApplicationActionListener
            )
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DrawableItemDecoration(ContextCompat.getDrawable(context, R.drawable.shape_divider)!!))
        }
    }
}