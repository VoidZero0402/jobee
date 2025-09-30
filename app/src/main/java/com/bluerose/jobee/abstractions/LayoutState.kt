package com.bluerose.jobee.abstractions

import com.bluerose.jobee.ui.components.ActionBar

enum class LayoutMode {
    FULL_SCREEN,
    ACTION_BAR,
    BOTTOM_NAV,
    ACTION_BAR_AND_BOTTOM_NAV
}

data class LayoutState(
    val layoutMode: LayoutMode = LayoutMode.FULL_SCREEN,
    val actionBarConfig: ActionBar.Config = ActionBar.Config(),
    val selectedNavItemPosition: Int = -1
)