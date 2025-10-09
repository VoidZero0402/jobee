package com.bluerose.jobee.ui.components

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import androidx.core.content.withStyledAttributes
import androidx.core.view.children
import androidx.core.view.isNotEmpty
import androidx.core.view.updateLayoutParams
import androidx.viewpager2.widget.ViewPager2
import com.bluerose.jobee.R
import com.bluerose.jobee.databinding.LayoutTabBinding
import com.bluerose.jobee.ui.utils.AnimationDuration

class TabLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : HorizontalScrollView(context, attrs, defStyleAttr) {
    private val binding = LayoutTabBinding.inflate(LayoutInflater.from(context), this)
    private val labels = arrayListOf<String>()
    private var onTabClicked: ((tab: Tab, position: Int) -> Unit)? = null

    private var viewPager: ViewPager2? = null
    private lateinit var selectedTab: Tab
    private lateinit var lastSelectedTab: Tab
    private var availableWidth = 0
    private var isFixedSizedMode = false

    init {
        context.withStyledAttributes(attrs, R.styleable.TabLayout, defStyleAttr, 0) {
            isHorizontalScrollBarEnabled = getBoolean(R.styleable.TabLayout_scrollable, false)
        }
    }

    private fun setupTabs() {
        labels.forEachIndexed { index, label ->
            val view = Tab(context).apply {
                text = label
                if (index == 0) {
                    activate()
                    selectedTab = this
                    lastSelectedTab = this
                }
                setOnClickListener {
                    selectTab(index)
                    onTabClicked?.invoke(this, index)
                }
            }
            binding.tabsContainer.addView(view)
        }
        updateTabsLayoutParams()
    }

    private fun updateTabsLayoutParams() {
        post {
            val totalTabsWidth = getTabsTotalWidth()
            if (totalTabsWidth <= availableWidth) {
                val tabWidth = availableWidth / binding.tabsContainer.childCount
                binding.tabsContainer.layoutParams = LayoutParams(availableWidth, ViewGroup.LayoutParams.WRAP_CONTENT)
                binding.tabsContainer.children.forEach {
                    it.updateLayoutParams {
                        width = tabWidth
                        height = ViewGroup.LayoutParams.WRAP_CONTENT
                    }
                }
                isFixedSizedMode = true
            } else {
                isHorizontalScrollBarEnabled = true
            }
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
        post {
            if (binding.tabsContainer.isNotEmpty()) {
                binding.indicator.updateLayoutParams {
                    width = when {
                        isFixedSizedMode -> selectedTab.layoutParams.width
                        else -> selectedTab.measuredWidth
                    }
                }
            }
        }
    }

    private fun updateIndicator() {
        post {
            val animators = arrayListOf<Animator>()
            val translationAnimator = ValueAnimator.ofFloat(binding.indicator.translationX, selectedTab.x)
            translationAnimator.addUpdateListener {
                binding.indicator.translationX = it.animatedValue as Float
            }
            animators.add(translationAnimator)
            if (!isFixedSizedMode) {
                val widthAnimator = ValueAnimator.ofFloat(
                    lastSelectedTab.measuredWidth.toFloat(),
                    selectedTab.measuredWidth.toFloat()
                )
                widthAnimator.addUpdateListener {
                    binding.indicator.updateLayoutParams {
                        width = (it.animatedValue as Float).toInt()
                    }
                }
                animators.add(widthAnimator)
            }
            val animatorSet = AnimatorSet()
            animatorSet.duration = AnimationDuration.LONG.duration.toLong()
            animatorSet.playTogether(*animators.toTypedArray())
            animatorSet.start()
        }
    }

    private fun updateScrollbar() {
        if (!isSelectedTabFullyVisible()) {
            smoothScrollTo(selectedTab.left, 0)
        }
    }

    private fun isSelectedTabFullyVisible(): Boolean {
        val rect = Rect()
        val isVisible = selectedTab.getGlobalVisibleRect(rect)
        return isVisible && rect.width() == selectedTab.measuredWidth && rect.height() == selectedTab.measuredHeight
    }

    private fun selectTab(position: Int) {
        val tab = binding.tabsContainer.children.elementAt(position) as Tab
        if (selectedTab == tab) return
        lastSelectedTab = selectedTab
        selectedTab = tab
        lastSelectedTab.deactivateAnimated()
        selectedTab.activateAnimated()
        updateIndicator()
        if (!isFixedSizedMode) {
            updateScrollbar()
        }
        viewPager?.setCurrentItem(position, true)
    }

    private fun setupViewPager() {
        viewPager?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                selectTab(position)
            }
        })
    }

    fun setTabs(vararg labels: String) {
        this.labels.addAll(labels)
        setupTabs()
        setupIndicator()
    }

    fun setViewPager(viewPager2: ViewPager2) {
        viewPager = viewPager2
        setupViewPager()
    }

    fun selectTabAtPosition(position: Int) {
        selectTab(position)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        availableWidth = w - paddingLeft - paddingRight
    }

    fun setOnTabClickedListener(l: (tab: Tab, position: Int) -> Unit) {
        onTabClicked = l
    }
}