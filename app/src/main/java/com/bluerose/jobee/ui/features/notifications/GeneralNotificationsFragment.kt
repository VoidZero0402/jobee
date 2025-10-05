package com.bluerose.jobee.ui.features.notifications

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bluerose.jobee.R
import com.bluerose.jobee.abstractions.BaseFragment
import com.bluerose.jobee.databinding.FragmentGeneralNotificationsVpBinding
import com.bluerose.jobee.di.Singletons
import com.bluerose.jobee.ui.utils.SpaceItemDecoration

class GeneralNotificationsFragment : BaseFragment<FragmentGeneralNotificationsVpBinding>() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.generalNotificationsRecycler.apply {
            adapter = NotificationAdapter(Singletons.repository.getNotifications().toMutableList())
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(SpaceItemDecoration(resources.getDimensionPixelSize(R.dimen.content_gap)))
        }
    }
}