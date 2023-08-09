package com.softeer.cartalog.ui.adapter

import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.BindingAdapter
import com.google.android.material.card.MaterialCardView
import com.softeer.cartalog.ui.activity.MainActivity
import com.softeer.cartalog.viewmodel.ExteriorViewModel
import com.softeer.cartalog.viewmodel.InteriorViewModel

@BindingAdapter("activity", "index")
fun setOnClickTabBtn(
    button: AppCompatButton,
    activity: MainActivity,
    idx: Int
) {
    button.setOnClickListener {
        activity.changeTab(idx)
    }
}

@BindingAdapter("adapter", "viewModel", "position")
fun setExteriorItemClickListener(
    cardView: MaterialCardView,
    adapter: ExteriorColorAdapter,
    viewModel: ExteriorViewModel,
    position: Int
) {
    cardView.setOnClickListener {

        if (adapter.selectedItem != position) {
            adapter.selectedItem = position
            viewModel.selectedColor.value = position
        }
        adapter.notifyDataSetChanged()
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

        if (adapter.selectedItem != position) {
            adapter.selectedItem = position
            viewModel.selectedColor.value = position
        }

        adapter.notifyDataSetChanged()
    }
}
