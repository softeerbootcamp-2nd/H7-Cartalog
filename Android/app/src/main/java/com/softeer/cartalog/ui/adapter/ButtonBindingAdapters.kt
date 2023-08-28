package com.softeer.cartalog.ui.adapter

import android.content.res.ColorStateList
import android.util.Log
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.FragmentContainerView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.material.card.MaterialCardView
import com.google.android.material.tabs.TabLayout
import com.softeer.cartalog.R
import com.softeer.cartalog.data.enums.OptionMode
import com.softeer.cartalog.data.model.estimate.SimilarEstimateOption
import com.softeer.cartalog.data.model.option.Option
import com.softeer.cartalog.data.model.summary.SummaryCarImage
import com.softeer.cartalog.viewmodel.EstimateViewModel
import com.softeer.cartalog.viewmodel.ExteriorViewModel
import com.softeer.cartalog.viewmodel.InteriorViewModel
import com.softeer.cartalog.viewmodel.OptionViewModel

@BindingAdapter("adapter", "viewModel", "position")
fun setExteriorItemClickListener(
    cardView: MaterialCardView,
    adapter: ExteriorColorAdapter,
    viewModel: ExteriorViewModel,
    position: Int
) {
    cardView.setOnClickListener {

        adapter.notifyItemChanged(adapter.selectedItem)
        if (adapter.selectedItem != position) {
            adapter.selectedItem = position
            viewModel.setSelectedColor(position)
        }
        adapter.notifyItemChanged(position)
    }
}

@BindingAdapter("adapter", "viewModel", "position")
fun setInteriorItemClickListener(
    cardView: MaterialCardView,
    adapter: InteriorColorAdapter,
    viewModel: InteriorViewModel,
    position: Int
) {
    cardView.setOnClickListener {

        adapter.notifyItemChanged(adapter.selectedItem)
        if (adapter.selectedItem != position) {
            adapter.selectedItem = position
            viewModel.setSelectedColor(position)
        }
        adapter.notifyItemChanged(position)
    }
}

@BindingAdapter("layout", "fragmentContainer", "idx")
fun setOnClickToggle(
    button: ImageButton,
    layout: ConstraintLayout,
    fragmentContainer: FragmentContainerView,
    idx: Int
) {

    button.setOnClickListener {
        if (layout.visibility == View.VISIBLE) {
            layout.animate()
                .alpha(0f)
                .setDuration(300)
                .withEndAction {
                    layout.visibility = View.GONE
                    layout.translationY = 0f
                    fragmentContainer.setPadding(0, 0, 0, 0)
                }
            button.animate().rotation(0f).start()
        } else {
            layout.animate()
                .alpha(1f)
                .setDuration(300)
                .withEndAction {
                    layout.visibility = View.VISIBLE
                    if (idx != 5) {
                        fragmentContainer.setPadding(0, 170, 0, 0)
                    }
                }
            button.animate().rotation(180f).start()
        }
    }
}

@BindingAdapter("adapter", "viewModel", "option")
fun setOptionItemClickListener(
    cardView: MaterialCardView,
    adapter: OptionSelectAdapter,
    viewModel: OptionViewModel,
    option: Option
) {
    cardView.setOnClickListener {
        if (viewModel.selectedSelectOption.value!!.contains(option)) {
            adapter.selectedItems.remove(option)
            viewModel.removeSelectedSelectOption(option)
            Log.d("TEST", "removed ${viewModel.selectedSelectOption.value}")
        } else {
            adapter.selectedItems.add(option)
            viewModel.addSelectedSelectOption(option)
            Log.d("TEST", "added ${viewModel.selectedSelectOption.value}")
        }
        adapter.notifyItemChanged(adapter.items.indexOf(option))
    }
}

@BindingAdapter("adapter", "viewModel", "option")
fun setSimilarOptionItemClickListener(
    cardView: MaterialCardView,
    adapter: EstimateOptionsAdapter,
    viewModel: EstimateViewModel,
    option: SimilarEstimateOption
) {
    cardView.setOnClickListener {
        if (viewModel.selectedOptions.value!!.contains(option)) {
            adapter.selectedItems.remove(option)
            viewModel.removeSelectedOption(option)
            Log.d("TEST", "removed ${viewModel.selectedOptions.value}")
        } else {
            adapter.selectedItems.add(option)
            viewModel.addSelectedOption(option)
            Log.d("TEST", "added ${viewModel.selectedOptions.value}")
        }
        adapter.notifyItemChanged(adapter.items.indexOf(option))
    }
}

@BindingAdapter("viewModel", "recyclerView")
fun setOptionTabSelected(
    tabLayout: TabLayout,
    viewModel: OptionViewModel,
    recyclerView: RecyclerView
) {
    tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab?) {

            when (tab?.position) {
                0 -> {
                    viewModel.setNowOptionMode(OptionMode.SELECT_OPTION)
                    recyclerView.setHasFixedSize(true)
                    recyclerView.adapter =
                        OptionSelectAdapter(viewModel, "전체").apply { setItems(viewModel.selectOptions!!) }
                }

                1 -> {
                    recyclerView.setHasFixedSize(true)
                    viewModel.setNowOptionMode(OptionMode.DEFAULT_OPTION)
                    recyclerView.adapter =
                        OptionDefaultAdapter(viewModel.defaultOptions!!)
                }
            }
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {}
        override fun onTabReselected(tab: TabLayout.Tab?) {}
    })
}

@BindingAdapter("viewModel", "recyclerView", "optionMode")
fun setOptionCategorySelected(
    radioGroup: RadioGroup,
    viewModel: OptionViewModel,
    recyclerView: RecyclerView,
    optionMode: OptionMode
) {
    radioGroup.setOnCheckedChangeListener { _, id ->
        val selected =
            radioGroup.findViewById<RadioButton>(id).text.toString()

        when (optionMode) {
            OptionMode.DEFAULT_OPTION -> {
                if (selected == "전체") {
                    recyclerView.adapter =
                        OptionDefaultAdapter(viewModel.defaultOptions!!)
                } else {
                    recyclerView.adapter =
                        OptionDefaultAdapter(viewModel.defaultOptions!!.filter {
                            it.childCategory?.contains(selected) ?: false
                        })
                }
            }

            OptionMode.SELECT_OPTION -> {
                recyclerView.adapter = OptionSelectAdapter(viewModel, selected).apply { setItems(viewModel.selectOptions!!) }
                recyclerView.adapter?.notifyDataSetChanged()
            }
        }
    }
}

@BindingAdapter("imageView", "imgUrl")
fun setCarImageSelected(
    radioGroup: RadioGroup,
    imageView: ImageView,
    carImage: SummaryCarImage?
) {
    radioGroup.setOnCheckedChangeListener { _, checkedId ->
        when (checkedId) {
            R.id.rb_exterior -> {
                imageView.load(carImage?.sideExteriorImageUrl)
            }

            R.id.rb_interior -> {
                imageView.load(carImage?.interiorImageUrl)
            }
        }
    }
}

@BindingAdapter("horizontalScrollView")
fun setConfirmCarImageSelected(
    radioGroup: RadioGroup,
    horizontalScrollView: HorizontalScrollView
) {
    radioGroup.setOnCheckedChangeListener { _, checkedId ->
        when (checkedId) {
            R.id.rb_exterior -> {
                horizontalScrollView.visibility = View.GONE
            }

            R.id.rb_interior -> {
                horizontalScrollView.visibility = View.VISIBLE
            }
        }
    }
}

@BindingAdapter("recyclerView", "button", "isEmpty")
fun setOnClickToggle(
    view: View,
    recyclerView: RecyclerView,
    button: ImageButton,
    isEmpty: Boolean,
) {
    if (!isEmpty) {
        if (recyclerView.visibility == View.VISIBLE) {
            button.rotation = 180f
        } else {
            button.rotation = 0f
        }

        val onClickListener = View.OnClickListener {
            if (recyclerView.visibility == View.VISIBLE) {
                recyclerView.visibility = View.GONE
                button.animate().rotation(0f).start()
            } else {
                recyclerView.visibility = View.VISIBLE
                button.animate().rotation(180f).start()
            }
        }
        view.setOnClickListener(onClickListener)
        button.setOnClickListener(onClickListener)
    }
}

@BindingAdapter("nestedScrollView")
fun setImageCheckBtn(
    button: AppCompatButton,
    nestedScrollView: NestedScrollView
) {

    nestedScrollView.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
        if (scrollY > oldScrollY) {
            button.visibility = View.VISIBLE
        } else {
            button.visibility = View.GONE
        }
    }

    button.setOnClickListener {
        nestedScrollView.smoothScrollTo(0, 0)
    }
}

@BindingAdapter("constraintLayout","imageView")
fun setInfoBtnClick(
    infoBtn: ImageView,
    constraintLayout: ConstraintLayout,
    guideBgr: ImageView
){
    infoBtn.setOnClickListener {
        if(constraintLayout.visibility == View.GONE){
            infoBtn.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(infoBtn.context, R.color.white))
            constraintLayout.visibility = View.VISIBLE
        } else {
            infoBtn.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(infoBtn.context, R.color.primary_color_900))
            constraintLayout.visibility = View.GONE
        }
    }

    constraintLayout.setOnClickListener {
        infoBtn.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(infoBtn.context, R.color.primary_color_900))
        constraintLayout.visibility = View.GONE
    }
    guideBgr.setOnClickListener(null)
}

@BindingAdapter("imageView")
fun setGuideClick(
    constraintLayout: ConstraintLayout,
    imageView: ImageView
){
    constraintLayout.setOnClickListener{
        constraintLayout.visibility = View.GONE
    }
    imageView.setOnClickListener(null)
}