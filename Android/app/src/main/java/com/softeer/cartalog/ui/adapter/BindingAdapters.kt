package com.softeer.cartalog.ui.adapter

import android.view.Gravity
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.softeer.cartalog.R
import com.softeer.cartalog.data.enums.OptionMode
import com.softeer.cartalog.ui.dialog.TypeDetailPopupActivity
import com.softeer.cartalog.util.UtilManager
import com.softeer.cartalog.viewmodel.ExteriorViewModel
import com.softeer.cartalog.viewmodel.InteriorViewModel
import com.softeer.cartalog.viewmodel.MainViewModel
import com.softeer.cartalog.viewmodel.OptionViewModel
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
    val adjustedOffsetPx = UtilManager.getViewPagerGap(viewPager)
    val trimItemAdapter = TypeDetailPopupAdapter(viewModel)
    with(viewPager) {
        offscreenPageLimit = 2
        adapter = trimItemAdapter
        setPageTransformer { page, position ->
            page.translationX = position * -adjustedOffsetPx
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

    val adjustedOffsetPx = UtilManager.getViewPagerGap(viewPager)
    val trimItemAdapter = TrimCardAdapter(viewModel)
    with(viewPager) {
        offscreenPageLimit = 2
        adapter = trimItemAdapter
        setPageTransformer { page, position ->
            page.translationX = position * -adjustedOffsetPx
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

@BindingAdapter("viewModel")
fun setExteriorColorRecyclerView(
    recyclerView: RecyclerView,
    viewModel: ExteriorViewModel
) {
    val adapter = ExteriorColorAdapter(viewModel)
    recyclerView.adapter = adapter
}

@BindingAdapter("viewModel")
fun setInteriorColorRecyclerView(
    recyclerView: RecyclerView,
    viewModel: InteriorViewModel
) {
    val adapter = InteriorColorAdapter(viewModel)
    recyclerView.adapter = adapter
}

@BindingAdapter("viewModel")
fun setOptionRecyclerView(
    recyclerView: RecyclerView,
    viewModel: OptionViewModel
) {
    when (viewModel.nowOptionMode.value) {
        OptionMode.SELECT_OPTION -> {
            recyclerView.adapter = OptionSelectAdapter(viewModel)
            recyclerView.adapter?.notifyDataSetChanged()
        }

        OptionMode.DEFAULT_OPTION -> {
            recyclerView.adapter = OptionDefaultAdapter(viewModel)
            recyclerView.adapter?.notifyDataSetChanged()
        }

        else -> {}
    }
}

@BindingAdapter("bottomSeekBar")
fun setHideSeekBar(
    topSeekBar: AppCompatSeekBar,
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

@BindingAdapter("idx", "button", "fragment")
fun setRangeBarVisibility(
    layout: ConstraintLayout,
    idx: Int,
    button: ImageButton,
    fragmentContainer: FragmentContainerView
) {
    if (idx == 1 && layout.visibility != View.GONE) {
        layout.animate()
            .alpha(0f)
            .setDuration(300)
            .withEndAction {
                layout.visibility = View.GONE
                layout.translationY = 0f
                fragmentContainer.setPadding(0, 0, 0, 0)
            }
        button.animate().rotation(0f).start()
    }
    if (idx == 5 && layout.visibility == View.GONE) {
        layout.visibility = View.VISIBLE
        layout.animate()
            .alpha(1f)
            .setDuration(300)
            .withEndAction { fragmentContainer.setPadding(0, 250, 0, 0) }
        button.animate().rotation(180f).start()
    }
}

@BindingAdapter("idx", "isExcess")
fun setRangeBarVisibility(
    seekBar: AppCompatSeekBar,
    idx: Int,
    isExcess: Boolean
) {
    if (idx == 5) {
        seekBar.isEnabled = false
        seekBar.thumb =
            AppCompatResources.getDrawable(seekBar.context, R.drawable.shape_seekbar_transparent)
    } else {
        seekBar.thumb = if (isExcess) {
            AppCompatResources.getDrawable(seekBar.context, R.drawable.shape_seekbar_thumb_excess)
        } else {
            AppCompatResources.getDrawable(seekBar.context, R.drawable.shape_seekbar_thumb)
        }
        seekBar.isEnabled = true
    }
}