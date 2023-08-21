package com.softeer.cartalog.ui.adapter

import android.view.View
import android.widget.HorizontalScrollView
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RadioGroup
import androidx.constraintlayout.widget.ConstraintLayout
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
                    if (idx == 5) {
                        fragmentContainer.setPadding(0, 250, 0, 0)
                    } else {
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
    adapter: OptionAdapter,
    viewModel: OptionViewModel,
    position: Int
) {
    cardView.setOnClickListener {
        when (viewModel.nowOptionMode.value) {
            OptionMode.SELECT_OPTION -> {
                adapter as OptionSelectAdapter
                if (adapter.selectedItem != position) {
                    adapter.selectedItem = position
                    viewModel.setSelectedSelectOption(position)
                }
                adapter.notifyDataSetChanged()
            }

            OptionMode.DEFAULT_OPTION -> {
                adapter as OptionDefaultAdapter
                if (adapter.selectedItem != position) {
                    adapter.selectedItem = position
                    viewModel.setSelectedDefaultOption(position)
                }
                adapter.notifyDataSetChanged()
            }

            else -> {}
        }
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
                    recyclerView.adapter =
                        OptionSelectAdapter(viewModel).apply { notifyDataSetChanged() }
                }

                1 -> {
                    viewModel.setNowOptionMode(OptionMode.DEFAULT_OPTION)
                    recyclerView.adapter =
                        OptionDefaultAdapter(viewModel).apply { notifyDataSetChanged() }
                }
            }
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {}
        override fun onTabReselected(tab: TabLayout.Tab?) {}
    })
}

@BindingAdapter("imageView","imgUrl")
fun setCarImageSelected(
    radioGroup: RadioGroup,
    imageView: ImageView,
    carImage: SummaryCarImage?
){
    radioGroup.setOnCheckedChangeListener { _, checkedId ->
        when(checkedId){
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
){
    radioGroup.setOnCheckedChangeListener { _, checkedId ->
        when(checkedId){
            R.id.rb_exterior -> {
                horizontalScrollView.visibility = View.GONE
            }
            R.id.rb_interior -> {
                horizontalScrollView.visibility = View.VISIBLE
            }
        }
    }
}

@BindingAdapter("recyclerView")
fun setOnClickToggle(
    button: ImageButton,
    recyclerView: RecyclerView
) {

    if (recyclerView.visibility == View.VISIBLE) {
        button.rotation = 180f
    } else {
        button.rotation = 0f
    }

    button.setOnClickListener {
        if (recyclerView.visibility == View.VISIBLE) {
            recyclerView.visibility = View.GONE
            button.animate().rotation(0f).start()
        } else {
            recyclerView.visibility = View.VISIBLE
            button.animate().rotation(180f).start()
        }
    }
}