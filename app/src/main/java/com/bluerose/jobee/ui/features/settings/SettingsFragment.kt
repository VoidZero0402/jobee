package com.bluerose.jobee.ui.features.settings

import android.os.Bundle
import android.view.View
import com.bluerose.jobee.R
import com.bluerose.jobee.abstractions.BaseFragment
import com.bluerose.jobee.abstractions.LayoutMode
import com.bluerose.jobee.abstractions.LayoutState
import com.bluerose.jobee.databinding.FragmentSettingsBinding
import com.bluerose.jobee.di.Singletons
import com.bluerose.jobee.ui.components.ActionBar
import com.bluerose.jobee.ui.features.auth.SignInFragment
import com.bluerose.jobee.ui.theme.ThemeManager

class SettingsFragment : BaseFragment<FragmentSettingsBinding>() {
    override val layoutState = LayoutState(
        LayoutMode.ACTION_BAR,
        lazy { ActionBar.Config(resources.getString(R.string.action_bar_settings)) }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.themeListItem.setOnClickListener {
            binding.themeSwitch.toggle()
        }

        binding.themeSwitch.apply {
            isChecked = isSystemDarkMode
            setOnCheckedChangedListener { isChecked ->
                Singletons.themeManager.setTheme(if (isChecked) ThemeManager.ThemeMode.NIGHT_MODE else ThemeManager.ThemeMode.LIGHT_MODE)
            }
        }

        binding.logoutListItem.setOnClickListener {
            Singletons.repository.signOutUser()
            navigateTo(SignInFragment())
        }
    }
}