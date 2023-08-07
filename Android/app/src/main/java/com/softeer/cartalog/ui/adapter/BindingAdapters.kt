package com.softeer.cartalog.ui.adapter

import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import androidx.navigation.NavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.softeer.cartalog.R
import com.softeer.cartalog.ui.dialog.TypeDetailPopupActivity
import com.softeer.cartalog.viewmodel.MainViewModel
import com.softeer.cartalog.viewmodel.TypeViewModel
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

@BindingAdapter("tabChange", "viewModel")
fun setOnTabChanged(
    tabLayout: TabLayout,
    navController: NavController,
    viewModel: MainViewModel
) {
    val font = ResourcesCompat.getFont(tabLayout.context, R.font.hyndaisans_head)

    tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            tab?.let {
                val selectedPosition = it.position
                val tv = TextView(tabLayout.context)

                when (selectedPosition) {
                    0 -> navController.navigate(R.id.trimFragment)
                    1 -> navController.navigate(R.id.typeFragment)
                    2 -> navController.navigate(R.id.exteriorFragment)
                    3 -> navController.navigate(R.id.interiorFragment)
                    4 -> navController.navigate(R.id.optionFragment)
                    5 -> navController.navigate(R.id.confirmFragment)
                }

                if (selectedPosition > viewModel.stepIndex.value!!) {
                    tv.setTextColor(
                        ContextCompat.getColor(
                            tabLayout.context,
                            R.color.primary_color_200
                        )
                    )
                    tv.typeface = font
                    tv.gravity = Gravity.CENTER
                    tv.text = tabLayout.getTabAt(selectedPosition - 1)?.text
                    tabLayout.getTabAt(selectedPosition - 1)?.customView = tv
                } else {
                    tabLayout.getTabAt(selectedPosition)?.customView = null
                }
                viewModel.setStepIndex(selectedPosition)
            }
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {}
        override fun onTabReselected(tab: TabLayout.Tab?) {}
    })
}

@BindingAdapter("navController")
fun showDialog(view: TextView, navController: NavController) {
    view.setOnClickListener {
        navController.navigate(R.id.typeDetailPopup)
    }
}

@BindingAdapter("activity")
fun closeDialog(view: ImageView, activity: TypeDetailPopupActivity) {
    view.setOnClickListener {
        activity.finish()
    }
}

@BindingAdapter("viewModel", "attachIndicator")
fun setTypeDetailViewPager(
    viewpager: ViewPager2,
    viewModel: TypeViewModel,
    indicator: DotsIndicator
) {

    val pageMarginPx = viewpager.context.resources.getDimensionPixelOffset(R.dimen.pageMargin)
    val pagerWidth = viewpager.context.resources.getDimensionPixelOffset(R.dimen.pagerWidth)
    val screenWidth = viewpager.context.resources.displayMetrics.widthPixels
    val offsetPx = screenWidth - pageMarginPx - pagerWidth

    val trimItemAdapter = TypeDetailPopupAdapter(viewModel)
    viewpager.offscreenPageLimit = 2
    viewpager.adapter = trimItemAdapter
    viewpager.setPageTransformer { page, position ->
        page.translationX = position * -offsetPx
    }
    viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            viewModel.changeSelectedTrim(position)
            trimItemAdapter.notifyItemRangeChanged(position - 1, 3)
        }
    })

    indicator.attachTo(viewpager)
}