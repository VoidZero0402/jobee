package com.bluerose.jobee.ui.features.settings

import android.os.Bundle
import android.view.View
import com.bluerose.jobee.R
import com.bluerose.jobee.abstractions.BaseFragment
import com.bluerose.jobee.abstractions.LayoutMode
import com.bluerose.jobee.abstractions.LayoutState
import com.bluerose.jobee.databinding.FragmentSettingsBinding
import com.bluerose.jobee.ui.components.ActionBar

class SettingsFragment : BaseFragment<FragmentSettingsBinding>() {
    override val layoutState = LayoutState(
        LayoutMode.ACTION_BAR,
        lazy { ActionBar.Config(resources.getString(R.string.action_bar_settings)) }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}