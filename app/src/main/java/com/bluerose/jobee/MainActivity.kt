package com.bluerose.jobee

import com.bluerose.jobee.abstractions.BaseActivity
import com.bluerose.jobee.abstractions.LayoutState
import com.bluerose.jobee.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun onViewCreated() {}

    override fun onLayoutStateChanged(state: LayoutState) {
        binding.fragmentContainer.applyLayoutMode(state.layoutMode)

        with(binding.actionBar) {
            if (state.layoutMode.hasActionBar) {
                showAnimated()
                applyConfig(state.actionBarConfig)
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
}