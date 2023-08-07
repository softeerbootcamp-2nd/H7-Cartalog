package com.softeer.cartalog.ui.adapter

import android.view.Gravity
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import androidx.navigation.NavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.softeer.cartalog.R
import com.softeer.cartalog.viewmodel.MainViewModel
import com.softeer.cartalog.viewmodel.TrimViewModel
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

@BindingAdapter("tabChange", "viewModel")
fun setOnTabChanged(
    tabLayout: TabLayout,
    navController: NavController,
    viewModel: MainViewModel
) {
    val font = ResourcesCompat.getFont(tabLayout.context, R.font.hyndaisans_head_bold)

    tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            tab?.let {
                val selectedPosition = it.position
                if (selectedPosition > viewModel.stepIndex.value!!) {

                    when (selectedPosition) {
                        0 -> navController.navigate(R.id.trimFragment)
                        1 -> navController.navigate(R.id.action_trimFragment_to_typeFragment)
                        2 -> navController.navigate(R.id.action_typeFragment_to_exteriorFragment)
                        3 -> navController.navigate(R.id.action_exteriorFragment_to_interiorFragment)
                        4 -> navController.navigate(R.id.action_interiorFragment_to_optionFragment)
                        5 -> navController.navigate(R.id.action_optionFragment_to_confirmFragment)
                    }

                    val tv = TextView(tabLayout.context)
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
                    when (selectedPosition) {
                        0 -> navController.navigate(R.id.action_typeFragment_to_trimFragment)
                        1 -> navController.navigate(R.id.action_exteriorFragment_to_typeFragment)
                        2 -> navController.navigate(R.id.action_interiorFragment_to_exteriorFragment)
                        3 -> navController.navigate(R.id.action_optionFragment_to_interiorFragment)
                        4 -> navController.navigate(R.id.action_confirmFragment_to_optionFragment)
                        5 -> navController.navigate(R.id.confirmFragment)
                    }
                    tabLayout.getTabAt(selectedPosition)?.customView = null
                }
                viewModel.setStepIndex(selectedPosition)
            }
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {}
        override fun onTabReselected(tab: TabLayout.Tab?) {}
    })
}

@BindingAdapter("viewModel", "attachIndicator")
fun setBindingViewPager(
    viewpager: ViewPager2,
    viewModel: TrimViewModel,
    indicator: DotsIndicator
) {

    val pageMarginPx = viewpager.context.resources.getDimensionPixelOffset(R.dimen.pageMargin)
    val pagerWidth = viewpager.context.resources.getDimensionPixelOffset(R.dimen.pagerWidth)
    val screenWidth = viewpager.context.resources.displayMetrics.widthPixels
    val offsetPx = screenWidth - pageMarginPx - pagerWidth

    val trimItemAdapter = TrimCardAdapter(viewModel)
    with(viewpager) {
        offscreenPageLimit = 2
        adapter = trimItemAdapter
        setPageTransformer { page, position ->
            page.translationX = position * -offsetPx
        }
        registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.changeSelectedTrim(position)
                trimItemAdapter.notifyItemRangeChanged(position - 1, 3)
            }
        })
    }
    indicator.attachTo(viewpager)
}
