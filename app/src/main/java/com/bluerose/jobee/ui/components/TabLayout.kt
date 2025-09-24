package com.bluerose.jobee.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import androidx.core.view.children
import androidx.core.view.isNotEmpty
import androidx.core.view.updateLayoutParams
import com.bluerose.jobee.databinding.LayoutTabBinding

class TabLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : HorizontalScrollView(context, attrs, defStyleAttr) {
    private val binding = LayoutTabBinding.inflate(LayoutInflater.from(context), this)
    private val tabItems = arrayListOf<TabItem>()
    private var onTabClicked: ((tab: Tab, tabItem: TabItem, position: Int) -> Unit)? = null

    data class TabItem(
        val id: String,
        val title: String
    )

    init {
        isHorizontalScrollBarEnabled = false
    }

    private fun setupTabs() {
        tabItems.forEachIndexed { index, tabItem ->
            val view = Tab(context).apply {
                text = tabItem.title
                if (index == 0) activate()
                setOnClickListener {
                    onTabClicked?.invoke(this, tabItem, index)
                }
            }
            binding.tabsContainer.addView(view)
        }
        updateTabsLayoutParams()
    }

    private fun updateTabsLayoutParams() {
        val screenWidth = resources.displayMetrics.widthPixels
        val totalTabsWidth = getTabsTotalWidth()
        if (totalTabsWidth <= screenWidth) {
            val tabWidth = screenWidth / binding.tabsContainer.childCount
            binding.tabsContainer.layoutParams = LayoutParams(screenWidth, ViewGroup.LayoutParams.WRAP_CONTENT)
            binding.tabsContainer.children.forEach {
                it.updateLayoutParams {
                    width = tabWidth
                    height = ViewGroup.LayoutParams.WRAP_CONTENT
                }
            }
        } else {
            isHorizontalScrollBarEnabled = true
        }
    }

    private fun getTabsTotalWidth(): Int {
        var totalWidth = 0
        binding.tabsContainer.children.forEach {
            it.measure(
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
            )
            totalWidth += it.measuredWidth
        }
        return totalWidth
    }

    private fun setupIndicator() {
        if (binding.tabsContainer.isNotEmpty()) {
            binding.indicator.updateLayoutParams {
                width = binding.tabsContainer.children.first().layoutParams.width
            }
        }
    }

    fun setTabs(vararg tabItems: TabItem) {
        this.tabItems.addAll(tabItems)
        setupTabs()
        setupIndicator()
    }

    fun setOnTabClickedListener(l: (tab: Tab, tabItem: TabItem, position: Int) -> Unit) {
        onTabClicked = l
    }
}