package com.bluerose.jobee.ui.features.profile

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.bluerose.jobee.R
import com.bluerose.jobee.abstractions.BaseFragment
import com.bluerose.jobee.abstractions.LayoutMode
import com.bluerose.jobee.abstractions.LayoutState
import com.bluerose.jobee.databinding.FragmentProfileBinding
import com.bluerose.jobee.ui.components.ActionBar
import com.bluerose.jobee.ui.constants.NavItemPositions
import com.bluerose.jobee.ui.features.settings.SettingsFragment

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    override val layoutState = LayoutState(
        LayoutMode.ACTION_BAR_AND_BOTTOM_NAV,
        lazy {
            ActionBar.Config(
                title = resources.getString(R.string.action_bar_profile),
                actions = listOf(
                    ActionBar.Action(
                        ContextCompat.getDrawable(requireContext(), R.drawable.ic_settings),
                        resources.getString(R.string.cd_settings)
                    ) {
                        navigateTo(SettingsFragment())
                    }
                )
            )
        },
        NavItemPositions.PROFILE
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}