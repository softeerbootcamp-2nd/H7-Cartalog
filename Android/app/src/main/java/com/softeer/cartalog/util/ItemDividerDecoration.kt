package com.softeer.cartalog.util

import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.softeer.cartalog.R

class ItemDividerDecoration() : RecyclerView.ItemDecoration() {
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        val widthMargin = 40f
        val height = 3f
        val left = parent.paddingLeft.toFloat()
        val right = parent.width - parent.paddingRight.toFloat()
        val paint =
            Paint().apply { color = ContextCompat.getColor(parent.context, R.color.blue_bg) }
        var view: View
        var top: Float
        var bottom: Float
        for (i in 0 until parent.childCount) {
            view = parent.getChildAt(i)
            top =
                view.bottom.toFloat() + (view.layoutParams as RecyclerView.LayoutParams).bottomMargin
            bottom = top + height
            c.drawRect(left + widthMargin, top, right - widthMargin, bottom, paint)
        }
    }
}