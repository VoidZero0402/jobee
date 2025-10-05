package com.bluerose.jobee

import com.bluerose.jobee.abstractions.BaseActivity
import com.bluerose.jobee.abstractions.BaseFragment
import com.bluerose.jobee.abstractions.LayoutState
import com.bluerose.jobee.databinding.ActivityMainBinding
import com.bluerose.jobee.ui.features.home.HomeFragment

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun onViewCreated() {
        navigateTo(HomeFragment(), addToBackStack = false)
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

    override fun onNavigate(fragment: BaseFragment<*>) {
        if (fragment.layoutState.layoutMode.hasActionBar) binding.actionBar.show() else binding.actionBar.hide()
        if (fragment.layoutState.layoutMode.hasBottomNavigation) binding.bottomNavigation.show() else binding.bottomNavigation.hide()
    }
}