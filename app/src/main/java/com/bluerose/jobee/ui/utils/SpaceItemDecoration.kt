package com.bluerose.jobee.ui.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpaceItemDecoration(
    private val space: Int,
    private val orientation: Int = RecyclerView.VERTICAL
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        if (position == RecyclerView.NO_POSITION || position == state.itemCount - 1) return

        if (orientation == RecyclerView.VERTICAL) {
            outRect.bottom = space
        } else {
            outRect.right = space
        }
    }
}