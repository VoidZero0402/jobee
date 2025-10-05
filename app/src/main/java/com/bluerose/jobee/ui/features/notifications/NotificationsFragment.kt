package com.bluerose.jobee.ui.features.notifications

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bluerose.jobee.R
import com.bluerose.jobee.abstractions.BaseFragment
import com.bluerose.jobee.abstractions.LayoutMode
import com.bluerose.jobee.abstractions.LayoutState
import com.bluerose.jobee.databinding.FragmentNotificationsBinding
import com.bluerose.jobee.ui.components.ActionBar

class NotificationsFragment : BaseFragment<FragmentNotificationsBinding>() {
    override val layoutState = LayoutState(
        LayoutMode.ACTION_BAR,
        lazy { ActionBar.Config(resources.getString(R.string.action_bar_notifications)) }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.notificationsTabs.setTabs(
            resources.getString(R.string.tab_general_notifications),
            resources.getString(R.string.tab_application_notifications)
        )

        binding.notificationsViewPager.apply {
            offscreenPageLimit = 2
            adapter = object : FragmentStateAdapter(this@NotificationsFragment) {
                private val fragments = listOf(GeneralNotificationsFragment(), ApplicationNotificationsFragment())

                override fun getItemCount(): Int = 2

                override fun createFragment(position: Int): Fragment {
                    return fragments[position]
                }
            }
        }

        binding.notificationsTabs.setViewPager(binding.notificationsViewPager)
    }
}