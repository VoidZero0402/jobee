package com.bluerose.jobee.ui.features.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bluerose.jobee.R
import com.bluerose.jobee.data.SampleRepository
import com.bluerose.jobee.databinding.LayoutHomeContentBinding
import com.bluerose.jobee.di.Singletons
import com.bluerose.jobee.ui.adapters.JobAdapter
import com.bluerose.jobee.ui.components.ChipGroup
import com.bluerose.jobee.ui.utils.Debouncer
import com.bluerose.jobee.ui.utils.SpaceItemDecoration
import kotlinx.coroutines.CoroutineScope

class HomeContentAdapter(
    private val lifecycleScope: CoroutineScope,
    private val onHomeContentEventListener: OnHomeContentEventListener
) : RecyclerView.Adapter<HomeContentAdapter.HomeContentViewHolder>() {

    inner class HomeContentViewHolder(binding: LayoutHomeContentBinding) : RecyclerView.ViewHolder(binding.root) {
        private val jobFilter = SampleRepository.JobFilter()

        init {
            binding.usernameText.text = Singletons.repository.getUser().username

            binding.recommendationRecycler.apply {
                adapter = JobAdapter(
                    Singletons.repository.getRecommendedJobs().toMutableList(),
                    onHomeContentEventListener.onJobActionListener
                )
                layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                addItemDecoration(
                    SpaceItemDecoration(
                        resources.getDimensionPixelSize(R.dimen.content_gap),
                        RecyclerView.HORIZONTAL
                    )
                )
            }

            val debouncer = Debouncer(lifecycleScope)
            binding.jobSearchField.setOnTextChangedListener {
                debouncer {
                    jobFilter.search = it
                    onHomeContentEventListener.onJobFilterChanged(jobFilter)
                }
            }

            binding.recentChipGroup.apply {
                setChips(
                    ChipGroup.ChipItem(resources.getString(R.string.text_all_category), true),
                    *Singletons.repository.getCategories().map { ChipGroup.ChipItem(it) }.toTypedArray()
                )
                setOnSelectionChangedListener {
                    jobFilter.category = if (it.isNotEmpty()) it[0].text.toString() else ""
                    onHomeContentEventListener.onJobFilterChanged(jobFilter)
                }
            }

            binding.notificationAction.setOnClickListener {
                onHomeContentEventListener.onNotificationActionClicked()
            }

            binding.recommendationAction.setOnClickListener {
                onHomeContentEventListener.onRecommendationJobsActionClicked()
            }

            binding.recentAction.setOnClickListener {
                onHomeContentEventListener.onRecentJobsActionClicked()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeContentViewHolder {
        val binding = LayoutHomeContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeContentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeContentViewHolder, position: Int) {}

    override fun getItemCount(): Int = 1

    interface OnHomeContentEventListener {
        val onJobActionListener: JobAdapter.OnJobActionListener

        fun onJobFilterChanged(filter: SampleRepository.JobFilter)
        fun onNotificationActionClicked()
        fun onRecommendationJobsActionClicked()
        fun onRecentJobsActionClicked()
    }
}