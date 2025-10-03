package com.bluerose.jobee.ui.utils

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView

class DrawableItemDecoration(
    private val drawable: Drawable,
    private val orientation: Int = RecyclerView.VERTICAL
) : RecyclerView.ItemDecoration() {

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        when (orientation) {
            RecyclerView.VERTICAL -> drawVerticalDivider(c, parent, state)
            RecyclerView.HORIZONTAL -> drawHorizontalDivider(c, parent, state)
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        if (position == RecyclerView.NO_POSITION || position == state.itemCount - 1) return

        if (orientation == RecyclerView.VERTICAL) {
            outRect.bottom = drawable.intrinsicHeight
        } else {
            outRect.right = drawable.intrinsicWidth
        }
    }

    private fun drawVerticalDivider(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        val lastItemPosition = state.itemCount - 1

        parent.children.forEach { child ->
            val position = parent.getChildAdapterPosition(child)
            if (position == RecyclerView.NO_POSITION || position == lastItemPosition) return@forEach

            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + drawable.intrinsicHeight

            drawable.apply {
                setBounds(left, top, right, bottom)
                draw(c)
            }
        }
    }

    private fun drawHorizontalDivider(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val top = parent.paddingTop
        val bottom = parent.height - parent.paddingBottom
        val lastItemPosition = state.itemCount - 1

        parent.children.forEach { child ->
            val position = parent.getChildAdapterPosition(child)
            if (position == RecyclerView.NO_POSITION || position == lastItemPosition) return@forEach

            val params = child.layoutParams as RecyclerView.LayoutParams
            val left = child.right + params.rightMargin
            val right = left + drawable.intrinsicWidth

            drawable.apply {
                setBounds(left, top, right, bottom)
                draw(c)
            }
        }
    }
}