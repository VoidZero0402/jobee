package com.bluerose.jobee.ui.features.jobs

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.PopupWindow
import androidx.recyclerview.widget.RecyclerView
import com.bluerose.jobee.R
import com.bluerose.jobee.data.SampleRepository
import com.bluerose.jobee.databinding.LayoutDiscoverJobsHeaderBinding
import com.bluerose.jobee.di.Singletons
import com.bluerose.jobee.ui.components.ChipGroup
import com.bluerose.jobee.ui.components.Dropdown
import com.bluerose.jobee.ui.utils.Debouncer
import kotlinx.coroutines.CoroutineScope

class DiscoverJobsHeaderAdapter(
    private val lifecycleScope: CoroutineScope,
    private val uiModel: DiscoverJobsHeaderUiModel
) : RecyclerView.Adapter<DiscoverJobsHeaderAdapter.HeaderViewHolder>() {

    private lateinit var binding: LayoutDiscoverJobsHeaderBinding

    data class DiscoverJobsHeaderUiModel(
        val jobsCount: Int
    )

    inner class HeaderViewHolder : RecyclerView.ViewHolder(binding.root) {
        private val jobFilter = SampleRepository.JobFilter()
        private val sortJobsDropdown = Dropdown(binding.root.context).apply {
            setOptions("Default", "Alphabetical (A to Z)", "Newly Posted", "Highest Salary", "Location")
        }
        private var isSortPopupShown = false

        init {
            val debouncer = Debouncer(lifecycleScope)
            binding.jobSearchField.setOnTextChangedListener {
                debouncer {
                    jobFilter.search = it
                }
            }

            binding.sortAction.setOnClickListener {
                if (!isSortPopupShown) {
                    val popup = PopupWindow(
                        sortJobsDropdown,
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT,
                        true
                    )

                    sortJobsDropdown.setOnOptionClickedListener {
                        popup.dismiss()
                    }

                    popup.apply {
                        elevation = 4f
                        showAsDropDown(it, -sortJobsDropdown.measuredWidth, 0, Gravity.END)
                        setOnDismissListener { isSortPopupShown = false }
                    }
                }
                isSortPopupShown = true
            }

            binding.categoryChipGroup.apply {
                setChips(
                    ChipGroup.ChipItem(resources.getString(R.string.text_all_category), true),
                    *Singletons.repository.getCategories().map { ChipGroup.ChipItem(it) }.toTypedArray()
                )
                setOnSelectionChangedListener {
                    jobFilter.category = if (it.isNotEmpty()) it[0].text.toString() else ""
                }
            }

            setUiModel(uiModel)
        }
    }

    fun setUiModel(uiModel: DiscoverJobsHeaderUiModel) {
        binding.jobsCountText.apply {
            text = resources.getString(R.string.jobs_founds_format, uiModel.jobsCount)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
        binding = LayoutDiscoverJobsHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HeaderViewHolder()
    }

    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {}

    override fun getItemCount(): Int = 1
}