package com.softeer.cartalog.ui.adapter

import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.fragment.app.FragmentContainerView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.tabs.TabLayout
import com.softeer.cartalog.data.enums.OptionMode
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
            viewModel.selectedColor.value = position
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
            viewModel.selectedColor.value = position
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
            layout.visibility = View.VISIBLE
            layout.animate()
                .alpha(1f)
                .setDuration(300)
                .withEndAction {
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
                    viewModel.selectedSelectOption.value = position
                }
                adapter.notifyDataSetChanged()
            }

            OptionMode.DEFAULT_OPTION -> {
                adapter as OptionDefaultAdapter
                if (adapter.selectedItem != position) {
                    adapter.selectedItem = position
                    viewModel.selectedDefaultOption.value = position
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
                    Log.d("TEST", viewModel.nowOptionMode.value.toString())
                    viewModel.nowOptionMode.value = OptionMode.SELECT_OPTION
                    recyclerView.adapter =
                        OptionSelectAdapter(viewModel).apply { notifyDataSetChanged() }
                }

                1 -> {
                    viewModel.nowOptionMode.value = OptionMode.DEFAULT_OPTION
                    recyclerView.adapter =
                        OptionDefaultAdapter(viewModel).apply { notifyDataSetChanged() }
                }
            }
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {}
        override fun onTabReselected(tab: TabLayout.Tab?) {}
    })
}
