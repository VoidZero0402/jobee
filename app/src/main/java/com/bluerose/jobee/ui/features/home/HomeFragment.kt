package com.bluerose.jobee.ui.features.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bluerose.jobee.R
import com.bluerose.jobee.abstractions.BaseFragment
import com.bluerose.jobee.abstractions.LayoutMode
import com.bluerose.jobee.abstractions.LayoutState
import com.bluerose.jobee.data.models.Job
import com.bluerose.jobee.databinding.FragmentHomeBinding
import com.bluerose.jobee.di.Singletons
import com.bluerose.jobee.ui.adapters.JobAdapter
import com.bluerose.jobee.ui.components.ChipGroup
import com.bluerose.jobee.ui.constants.NavItemPositions
import com.bluerose.jobee.ui.features.notifications.NotificationsFragment
import com.bluerose.jobee.ui.utils.SpaceItemDecoration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override val layoutState = LayoutState(
        LayoutMode.BOTTOM_NAV,
        selectedNavItemPosition = NavItemPositions.HOME
    )
    private lateinit var recentJobAdapter: JobAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.usernameText.text = Singletons.repository.getUser().username

        val onJobActionListener = object : JobAdapter.OnJobActionListener {
            override fun onJobClick(job: Job) {}

            override fun onJobSaved(job: Job) {
                Singletons.repository.saveJob(job.id)
            }

            override fun onJobUnsaved(job: Job) {
                Singletons.repository.unsaveJob(job.id)
            }
        }

        binding.recommendationRecycler.apply {
            adapter = JobAdapter(
                Singletons.repository.getRecommendedJobs().toMutableList(),
                onJobActionListener
            )
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            addItemDecoration(
                SpaceItemDecoration(
                    resources.getDimensionPixelSize(R.dimen.content_gap),
                    RecyclerView.HORIZONTAL
                )
            )
        }

        binding.recentChipGroup.apply {
            setChips(
                ChipGroup.ChipItem(resources.getString(R.string.text_all_category), true),
                *Singletons.repository.getCategories().map { ChipGroup.ChipItem(it) }.toTypedArray()
            )
            setOnSelectionChangedListener {
                lifecycleScope.launch(Dispatchers.Default) {
                    val jobs = Singletons.repository.getJobs(category = it[0].text.toString()).toMutableList()
                    withContext(Dispatchers.Main) {
                        recentJobAdapter.setJobs(jobs)
                    }
                }
            }
        }

        recentJobAdapter = JobAdapter(
            Singletons.repository.getJobs().toMutableList(),
            onJobActionListener
        )

        binding.recentRecycler.apply {
            adapter = recentJobAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(SpaceItemDecoration(resources.getDimensionPixelSize(R.dimen.content_gap)))
        }

        binding.notificationAction.setOnClickListener {
            navigateTo(NotificationsFragment())
        }
    }
}