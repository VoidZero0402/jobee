package com.bluerose.jobee.ui.features.notifications

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bluerose.jobee.R
import com.bluerose.jobee.abstractions.BaseActivity
import com.bluerose.jobee.abstractions.BaseFragment
import com.bluerose.jobee.data.models.Application
import com.bluerose.jobee.databinding.FragmentApplicationNotificationsVpBinding
import com.bluerose.jobee.di.Singletons
import com.bluerose.jobee.ui.adapters.ApplicationAdapter
import com.bluerose.jobee.ui.constants.BundleKeys
import com.bluerose.jobee.ui.features.applications.ApplicationStatusFragment
import com.bluerose.jobee.ui.utils.DrawableItemDecoration

class ApplicationNotificationsFragment : BaseFragment<FragmentApplicationNotificationsVpBinding>() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val onApplicationActionListener = object : ApplicationAdapter.OnApplicationActionListener {
            override fun onApplicationClick(application: Application) {
                val bundle = Bundle()
                bundle.putParcelable(BundleKeys.APPLICATION, application)
                (activity as? BaseActivity<*>)?.navigateTo(ApplicationStatusFragment(), bundle)
            }
        }

        binding.applicationNotificationsRecycler.apply {
            adapter = ApplicationAdapter(
                Singletons.repository.getApplications().toMutableList(),
                onApplicationActionListener
            )
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DrawableItemDecoration(ContextCompat.getDrawable(context, R.drawable.shape_divider)!!))
        }
    }
}