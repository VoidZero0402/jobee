package com.bluerose.jobee.ui.features.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.bluerose.jobee.R
import com.bluerose.jobee.abstractions.BaseFragment
import com.bluerose.jobee.abstractions.LayoutMode
import com.bluerose.jobee.abstractions.LayoutState
import com.bluerose.jobee.data.SampleRepository
import com.bluerose.jobee.data.models.Job
import com.bluerose.jobee.databinding.FragmentHomeBinding
import com.bluerose.jobee.di.Singletons
import com.bluerose.jobee.ui.adapters.JobAdapter
import com.bluerose.jobee.ui.constants.BundleKeys
import com.bluerose.jobee.ui.constants.NavItemPositions
import com.bluerose.jobee.ui.features.jobs.JobDetailsFragment
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

        val onJobActionListener = object : JobAdapter.OnJobActionListener {
            override fun onJobClick(job: Job) {
                val bundle = Bundle()
                bundle.putParcelable(BundleKeys.JOB, job)
                navigateTo(JobDetailsFragment(), bundle)
            }

            override fun onJobSaved(job: Job) {
                Singletons.repository.saveJob(job.id)
            }

            override fun onJobUnsaved(job: Job) {
                Singletons.repository.unsaveJob(job.id)
            }
        }

        val onHomeContentEventListener = object : HomeContentAdapter.OnHomeContentEventListener {
            override val onJobActionListener = onJobActionListener

            override fun onJobFilterChanged(filter: SampleRepository.JobFilter) {
                lifecycleScope.launch(Dispatchers.Default) {
                    val jobs = Singletons.repository.getJobs(filter).toMutableList()
                    withContext(Dispatchers.Main) {
                        recentJobAdapter.setJobs(jobs)
                        binding.notFoundJobs.root.visibility = if (jobs.isNotEmpty()) View.GONE else View.VISIBLE
                    }
                }
            }

            override fun onNotificationActionClicked() {
                navigateTo(NotificationsFragment())
            }

            override fun onRecommendationJobsActionClicked() {}

            override fun onRecentJobsActionClicked() {}
        }

        recentJobAdapter = JobAdapter(
            Singletons.repository.getJobs().toMutableList(),
            onJobActionListener
        )

        binding.rootRecycler.apply {
            adapter = ConcatAdapter(HomeContentAdapter(lifecycleScope, onHomeContentEventListener), recentJobAdapter)
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(SpaceItemDecoration(resources.getDimensionPixelSize(R.dimen.content_gap)))
        }
    }
}