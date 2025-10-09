package com.bluerose.jobee

import com.bluerose.jobee.abstractions.BaseActivity
import com.bluerose.jobee.abstractions.LayoutState
import com.bluerose.jobee.databinding.ActivityMainBinding
import com.bluerose.jobee.di.Singletons
import com.bluerose.jobee.ui.components.ActionBar
import com.bluerose.jobee.ui.constants.NavItemPositions
import com.bluerose.jobee.ui.features.applications.ApplicationsFragment
import com.bluerose.jobee.ui.features.auth.SignInFragment
import com.bluerose.jobee.ui.features.home.HomeFragment
import com.bluerose.jobee.ui.features.jobs.SavedJobsFragment
import com.bluerose.jobee.ui.features.messages.MessagesFragment
import com.bluerose.jobee.ui.features.profile.ProfileFragment

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun onViewCreated() {
        val destination = if (Singletons.repository.isUserAuthorized()) HomeFragment() else SignInFragment()
        navigateTo(destination, addToBackStack = false)

        binding.bottomNavigation.setOnNavItemSelectedListener { _, position ->
            val fragment = when (position) {
                NavItemPositions.HOME -> HomeFragment()
                NavItemPositions.SAVED_JOBS -> SavedJobsFragment()
                NavItemPositions.APPLICATIONS -> ApplicationsFragment()
                NavItemPositions.MESSAGES -> MessagesFragment()
                NavItemPositions.PROFILE -> ProfileFragment()
                else -> HomeFragment()
            }
            navigateTo(fragment)
        }

    }

    override fun onLayoutStateChanged(state: LayoutState) {
        binding.fragmentContainer.applyLayoutMode(state.layoutMode)

        with(binding.actionBar) {
            if (state.layoutMode.hasActionBar) {
                show()
                applyConfig(state.actionBarConfig.value)
            } else {
                hide()
            }
        }

        with(binding.bottomNavigation) {
            if (state.layoutMode.hasBottomNavigation) {
                show()
                selectNavItemAtPosition(state.selectedNavItemPosition)
            } else {
                hide()
            }
        }
    }

    override fun getLayoutActionBar(): ActionBar {
        return binding.actionBar
    }
}