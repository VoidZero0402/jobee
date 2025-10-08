package com.bluerose.jobee.ui.features.jobs

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bluerose.jobee.R
import com.bluerose.jobee.abstractions.BaseFragment
import com.bluerose.jobee.abstractions.LayoutMode
import com.bluerose.jobee.abstractions.LayoutState
import com.bluerose.jobee.data.models.Job
import com.bluerose.jobee.databinding.FragmentSavedJobsBinding
import com.bluerose.jobee.di.Singletons
import com.bluerose.jobee.ui.adapters.JobAdapter
import com.bluerose.jobee.ui.components.ActionBar
import com.bluerose.jobee.ui.constants.BundleKeys
import com.bluerose.jobee.ui.constants.NavItemPositions
import com.bluerose.jobee.ui.utils.SpaceItemDecoration

class SavedJobsFragment : BaseFragment<FragmentSavedJobsBinding>() {
    override val layoutState = LayoutState(
        LayoutMode.ACTION_BAR_AND_BOTTOM_NAV,
        lazy {
            ActionBar.Config(
                title = resources.getString(R.string.action_bar_saved_jobs),
                actions = listOf(
                    ActionBar.Action(
                        ContextCompat.getDrawable(requireContext(), R.drawable.ic_magnifier),
                        resources.getString(R.string.cd_search),
                        null
                    )
                )
            )
        },
        NavItemPositions.SAVED_JOBS
    )
    private lateinit var savedJobsAdapter: JobAdapter

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
                savedJobsAdapter.removeJob(job)
            }
        }

        savedJobsAdapter = JobAdapter(Singletons.repository.getSavedJobs().toMutableList(), onJobActionListener)

        binding.savedJobsRecycler.apply {
            adapter = savedJobsAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(SpaceItemDecoration(resources.getDimensionPixelSize(R.dimen.content_gap)))
        }
    }
}