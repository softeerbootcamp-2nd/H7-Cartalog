package com.softeer.cartalog.ui.adapter

import android.view.View
import android.widget.HorizontalScrollView
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.NestedScrollView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.FragmentContainerView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.material.card.MaterialCardView
import com.google.android.material.tabs.TabLayout
import com.softeer.cartalog.R
import com.softeer.cartalog.data.enums.OptionMode
import com.softeer.cartalog.data.model.SummaryCarImage
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
                        fragmentContainer.setPadding(0, 150, 0, 0)
                    }
                }
            button.animate().rotation(180f).start()
        }
    }
}

@BindingAdapter("adapter", "viewModel", "position")
fun setOptionItemClickListener(
    cardView: MaterialCardView,
    adapter: OptionSelectAdapter,
    viewModel: OptionViewModel,
    position: Int
) {
    cardView.setOnClickListener {
        if (viewModel.selectedSelectOption.value!!.contains(viewModel.selectOptions?.get(position)!!)) {
            adapter.selectedItems.remove(position)
            viewModel.removeSelectedSelectOption(viewModel.selectOptions?.get(position)!!)
        } else {
            adapter.selectedItems.add(position)
            viewModel.addSelectedSelectOption(viewModel.selectOptions?.get(position)!!)
        }
        adapter.notifyItemChanged(position)
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
                        OptionSelectAdapter(viewModel, "전체")
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
                recyclerView.adapter = OptionSelectAdapter(viewModel, selected)
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