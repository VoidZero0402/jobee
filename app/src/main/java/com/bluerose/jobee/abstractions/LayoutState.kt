package com.bluerose.jobee.abstractions

import com.bluerose.jobee.ui.components.ActionBar

enum class LayoutMode {
    FULL_SCREEN,
    ACTION_BAR,
    BOTTOM_NAV,
    ACTION_BAR_AND_BOTTOM_NAV;

    val hasActionBar get() = this == ACTION_BAR || this == ACTION_BAR_AND_BOTTOM_NAV
    val hasBottomNavigation get() = this == BOTTOM_NAV || this == ACTION_BAR_AND_BOTTOM_NAV
}

data class LayoutState(
    val layoutMode: LayoutMode = LayoutMode.FULL_SCREEN,
    val actionBarConfig: Lazy<ActionBar.Config> = lazy { ActionBar.Config() },
    val selectedNavItemPosition: Int = -1
)