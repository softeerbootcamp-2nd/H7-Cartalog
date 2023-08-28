package com.softeer.cartalog.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemHorizontalSpacingDecoration(private val spacing: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        if (parent.getChildAdapterPosition(view) != (parent.adapter?.itemCount!! - 1)) {
            outRect.left = spacing
        } else {
            outRect.left = spacing
            outRect.right = spacing
        }
    }
}