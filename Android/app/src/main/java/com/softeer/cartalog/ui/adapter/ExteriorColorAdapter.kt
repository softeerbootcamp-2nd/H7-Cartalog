package com.softeer.cartalog.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softeer.cartalog.databinding.ItemExteriorColorBinding
import com.softeer.cartalog.model.data.CarColor
import com.softeer.cartalog.viewmodel.ExteriorViewModel

class ExteriorColorAdapter(private val viewModel: ExteriorViewModel) :
    RecyclerView.Adapter<ExteriorColorAdapter.ExteriorColorViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExteriorColorAdapter.ExteriorColorViewHolder {
        val binding =
            ItemExteriorColorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExteriorColorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExteriorColorViewHolder, position: Int) {
        val currentItem = viewModel.colorList.value?.get(position)
        holder.bind(currentItem, position)
    }

    override fun getItemCount(): Int {
        return viewModel.colorList.value!!.size
    }

    inner class ExteriorColorViewHolder(val binding: ItemExteriorColorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var isSelected = false
        fun bind(item: CarColor?, position: Int) {
            isSelected = position == viewModel.selectedColor.value
            binding.isSelected = isSelected
            binding.carColor = item
        }
    }
}