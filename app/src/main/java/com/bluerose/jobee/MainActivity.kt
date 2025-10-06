package com.bluerose.jobee

import com.bluerose.jobee.abstractions.BaseActivity
import com.bluerose.jobee.abstractions.BaseFragment
import com.bluerose.jobee.abstractions.LayoutState
import com.bluerose.jobee.abstractions.doOnAttach
import com.bluerose.jobee.databinding.ActivityMainBinding
import com.bluerose.jobee.di.Singletons
import com.bluerose.jobee.ui.constants.NavItemPositions
import com.bluerose.jobee.ui.features.applications.ApplicationsFragment
import com.bluerose.jobee.ui.features.auth.SignInFragment
import com.bluerose.jobee.ui.features.home.HomeFragment
import com.bluerose.jobee.ui.features.messages.MessagesFragment
import com.bluerose.jobee.ui.features.profile.ProfileFragment
import com.bluerose.jobee.ui.features.savedjobs.SavedJobsFragment

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun onViewCreated() {
        val destination = if (Singletons.repository.isUserAuthorized()) HomeFragment() else SignInFragment()
        navigateTo(destination.doOnAttach(supportFragmentManager) { applyInitialLayout(it) }, addToBackStack = false)

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
                showAnimated()
                applyConfig(state.actionBarConfig.value)
            } else {
                hideAnimated()
            }
        }

        with(binding.bottomNavigation) {
            if (state.layoutMode.hasBottomNavigation) {
                showAnimated()
                selectNavItemAtPosition(state.selectedNavItemPosition)
            } else {
                hideAnimated()
            }
        }
    }

    private fun applyInitialLayout(fragment: BaseFragment<*>) {
        if (fragment.layoutState.layoutMode.hasActionBar) binding.actionBar.show() else binding.actionBar.hide()
        if (fragment.layoutState.layoutMode.hasBottomNavigation) binding.bottomNavigation.show() else binding.bottomNavigation.hide()
    }
}