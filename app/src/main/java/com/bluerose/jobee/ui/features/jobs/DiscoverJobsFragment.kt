package com.bluerose.jobee.ui.features.jobs

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
import com.bluerose.jobee.databinding.FragmentDiscoverJobsBinding
import com.bluerose.jobee.di.Singletons
import com.bluerose.jobee.ui.adapters.JobAdapter
import com.bluerose.jobee.ui.constants.BundleKeys
import com.bluerose.jobee.ui.constants.NavItemPositions
import com.bluerose.jobee.ui.utils.SpaceItemDecoration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DiscoverJobsFragment : BaseFragment<FragmentDiscoverJobsBinding>() {
    override val layoutState = LayoutState(
        LayoutMode.BOTTOM_NAV,
        selectedNavItemPosition = NavItemPositions.DISCOVER_JOBS
    )
    private lateinit var discoverJobsHeaderAdapter: DiscoverJobsHeaderAdapter
    private lateinit var jobAdapter: JobAdapter

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

        val onDiscoverJobsHeaderEventListener = object : DiscoverJobsHeaderAdapter.OnDiscoverJobsHeaderEventListener {
            override fun onJobFilterChanged(filter: SampleRepository.JobFilter) {
                lifecycleScope.launch(Dispatchers.Default) {
                    val jobs = Singletons.repository.getJobs(filter).toMutableList()
                    withContext(Dispatchers.Main) {
                        jobAdapter.setJobs(jobs)
                        discoverJobsHeaderAdapter.setUiModel(
                            DiscoverJobsHeaderAdapter.DiscoverJobsHeaderUiModel(
                                jobAdapter.itemCount
                            )
                        )
                        binding.notFoundJobs.root.visibility = if (jobs.isNotEmpty()) View.GONE else View.VISIBLE
                    }
                }
            }
        }

        jobAdapter = JobAdapter(Singletons.repository.getJobs().toMutableList(), onJobActionListener)

        discoverJobsHeaderAdapter = DiscoverJobsHeaderAdapter(
            lifecycleScope,
            DiscoverJobsHeaderAdapter.DiscoverJobsHeaderUiModel(jobAdapter.itemCount),
            onDiscoverJobsHeaderEventListener
        )

        binding.rootRecycler.apply {
            adapter = ConcatAdapter(discoverJobsHeaderAdapter, jobAdapter)
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(SpaceItemDecoration(resources.getDimensionPixelSize(R.dimen.content_gap)))
        }
    }
}