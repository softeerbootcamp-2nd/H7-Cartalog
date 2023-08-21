package com.softeer.cartalog.ui.adapter

import android.view.View
import android.widget.ImageButton
import android.widget.SeekBar
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.fragment.app.FragmentContainerView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.softeer.cartalog.R
import com.softeer.cartalog.data.model.Options
import com.softeer.cartalog.data.enums.OptionMode
import com.softeer.cartalog.data.model.CarColor
import com.softeer.cartalog.data.model.ConfirmDetail
import com.softeer.cartalog.data.model.SummaryOptionPrice
import com.softeer.cartalog.data.model.db.PriceData
import com.softeer.cartalog.util.ItemDividerDecoration
import com.softeer.cartalog.util.ItemVerticalSpacingDecoration
import com.softeer.cartalog.util.UtilManager
import com.softeer.cartalog.viewmodel.ExteriorViewModel
import com.softeer.cartalog.viewmodel.InteriorViewModel
import com.softeer.cartalog.viewmodel.MainViewModel
import com.softeer.cartalog.viewmodel.OptionViewModel
import com.softeer.cartalog.viewmodel.PriceSummaryViewModel
import com.softeer.cartalog.viewmodel.TrimViewModel
import com.softeer.cartalog.viewmodel.TypeViewModel
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

@BindingAdapter("viewModel", "indicator", "selectedType")
fun setTypeDetailViewPager(
    viewPager: ViewPager2,
    viewModel: TypeViewModel,
    indicator: DotsIndicator,
    selectedType: Int
) {
    val adjustedOffsetPx = UtilManager.getViewPagerGap(viewPager)
    val typeDetailItemAdapter = TypeDetailPopupAdapter(viewModel, selectedType)
    with(viewPager) {
        offscreenPageLimit = 2
        adapter = typeDetailItemAdapter
        setPageTransformer { page, position ->
            page.translationX = position * -adjustedOffsetPx
        }
        registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                typeDetailItemAdapter.notifyItemRangeChanged(position - 1, 3)
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

@BindingAdapter("viewModel", "colorList")
fun setExteriorColorRecyclerView(
    recyclerView: RecyclerView,
    viewModel: ExteriorViewModel,
    colorList: List<CarColor>?
) {
    val adapter = ExteriorColorAdapter(viewModel)
    recyclerView.adapter = adapter
}

@BindingAdapter("viewModel", "colorList")
fun setInteriorColorRecyclerView(
    recyclerView: RecyclerView,
    viewModel: InteriorViewModel,
    colorList: List<CarColor>?
) {
    val adapter = InteriorColorAdapter(viewModel)
    recyclerView.adapter = adapter
}

@BindingAdapter("viewModel", "optionList")
fun setOptionRecyclerView(
    recyclerView: RecyclerView,
    viewModel: OptionViewModel,
    optionList: Options?
) {
    // 처음 optionfragment 열렸을 때 추가옵션 세팅을 위한 함수
    recyclerView.setHasFixedSize(true)
    recyclerView.adapter = OptionSelectAdapter(viewModel, "전체")
    recyclerView.adapter?.notifyDataSetChanged()
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

    seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekbar: SeekBar?, progress: Int, fromUser: Boolean) {
            viewModel.setRangeLimit(progress)
        }

        override fun onStartTrackingTouch(p0: SeekBar?) {
        }

        override fun onStopTrackingTouch(p0: SeekBar?) {
        }

    })
}

@BindingAdapter("idx", "button", "fragmentContainer")
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
        layout.animate()
            .alpha(1f)
            .setDuration(300)
            .withEndAction {
                layout.visibility = View.VISIBLE
                fragmentContainer.setPadding(0, 0, 0, 0)
            }
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

@BindingAdapter("viewModel")
fun setSummaryOptionsRecyclerView(
    recyclerView: RecyclerView,
    viewModel: PriceSummaryViewModel
) {
    val optionList = viewModel.options.value!!
    val adapter = if (optionList.isEmpty()) {
        SummaryOptionsAdapter(listOf(SummaryOptionPrice("-", -1)))
    } else {
        SummaryOptionsAdapter(optionList)
    }
    recyclerView.adapter = adapter
}

@BindingAdapter("listData")
fun setConfirmDetailTitleRecyclerView(
    recyclerView: RecyclerView,
    data: List<ConfirmDetail>?
) {
    if (data != null) {
        val adapter = ConfirmDetailTitleAdapter(data)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(ItemVerticalSpacingDecoration(16))
    }
}

@BindingAdapter("listData")
fun setConfirmDetailContentsRecyclerView(
    recyclerView: RecyclerView,
    data: List<PriceData>?
) {
    if (data != null) {
        val adapter = ConfirmDetailContentsAdapter(data)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(ItemDividerDecoration())
        recyclerView.visibility = View.VISIBLE
    } else {
        recyclerView.visibility = View.GONE
    }
}

@BindingAdapter("hashTags")
fun setHashTagChipGroup(
    chipGroup: ChipGroup,
    hashTags: List<String>?
) {
    hashTags?.let {
        chipGroup.removeAllViews()
        for (hashTag in it) {
            chipGroup.addView(Chip(chipGroup.context).apply {
                text = hashTag
            })
        }
    }

}