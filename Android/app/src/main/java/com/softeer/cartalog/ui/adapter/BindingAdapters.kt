package com.softeer.cartalog.ui.adapter

import android.view.Gravity
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatSeekBar
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

@BindingAdapter("navController")
fun setShowDialog(view: TextView, navController: NavController) {
    view.setOnClickListener {
        navController.navigate(R.id.typeDetailPopup)
    }
}

@BindingAdapter("activity")
fun setCloseDialog(view: ImageView, activity: TypeDetailPopupActivity) {
    view.setOnClickListener {
        activity.finish()
    }
}

@BindingAdapter("viewModel", "indicator")
fun setTypeDetailViewPager(
    viewPager: ViewPager2,
    viewModel: TypeViewModel,
    indicator: DotsIndicator
) {

    val pageMarginPx = viewPager.context.resources.getDimensionPixelOffset(R.dimen.pageMargin)
    val pagerWidth = viewPager.context.resources.getDimensionPixelOffset(R.dimen.pagerWidth)
    val screenWidth = viewPager.context.resources.displayMetrics.widthPixels
    val offsetPx = screenWidth - pageMarginPx - pagerWidth

    val trimItemAdapter = TypeDetailPopupAdapter(viewModel)
    with(viewPager) {
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
    indicator.attachTo(viewPager)
}

@BindingAdapter("viewModel", "indicator")
fun setTrimCardViewPager(
    viewPager: ViewPager2,
    viewModel: TrimViewModel,
    indicator: DotsIndicator
) {

    val pageMarginPx = viewPager.context.resources.getDimensionPixelOffset(R.dimen.pageMargin)
    val pagerWidth = viewPager.context.resources.getDimensionPixelOffset(R.dimen.pagerWidth)
    val screenWidth = viewPager.context.resources.displayMetrics.widthPixels
    val offsetPx = screenWidth - pageMarginPx - pagerWidth

    val trimItemAdapter = TrimCardAdapter(viewModel)
    with(viewPager) {
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
    indicator.attachTo(viewPager)
}

@BindingAdapter("bottomSeekBar")
fun setHideSeekBar(
    topSeekBar: SeekBar,
    bottomSeekBar: AppCompatSeekBar
) {
    topSeekBar.setOnTouchListener { _, event ->
        bottomSeekBar.dispatchTouchEvent(event)
        true
    }
}

@BindingAdapter("viewModel")
fun setBudgetLimit(
    seekBar: AppCompatSeekBar,
    viewModel: MainViewModel
) {
    // 받아온 데이터로 설정
    //val minValue = 3850
    //val maxValue = 4300

    seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekbar: SeekBar?, progress: Int, fromUser: Boolean) {
            // progress 값으로 비교해 놓은 상태
            //val mappedValue = minValue + (maxValue - minValue) * progress / 100
            viewModel.budgetRangeLimit.value = progress
            viewModel.isExcess.value =
                viewModel.budgetRangeLimit.value!! < viewModel.totalPrice.value!!
        }

        override fun onStartTrackingTouch(p0: SeekBar?) {
        }

        override fun onStopTrackingTouch(p0: SeekBar?) {
        }

    })
}