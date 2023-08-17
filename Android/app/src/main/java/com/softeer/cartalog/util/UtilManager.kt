package com.softeer.cartalog.util

import android.content.Context
import androidx.viewpager2.widget.ViewPager2
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import com.softeer.cartalog.R

class UtilManager {
    companion object {
        fun get360Image(colorCode: String, imgIndex: Int): String {
            return "https://want-car-image.s3.ap-northeast-2.amazonaws.com/palisade/exterior/${colorCode}/${
                String.format("%03d", imgIndex)}.png"
        }


        fun getViewPagerGap(viewPager: ViewPager2): Int {
            val pageMarginPx =
                viewPager.context.resources.getDimensionPixelOffset(R.dimen.page_margin)
            val pagerWidth =
                viewPager.context.resources.getDimensionPixelOffset(R.dimen.pager_width)
            val screenWidth = viewPager.context.resources.displayMetrics.widthPixels
            val offsetPx = screenWidth - pageMarginPx - pagerWidth

            val screenWidthDp = screenWidth / viewPager.context.resources.displayMetrics.density
            val referenceWidthDp = 900
            val scaleFactor = screenWidthDp / referenceWidthDp

            return (offsetPx * scaleFactor).toInt()


        }
    }
}