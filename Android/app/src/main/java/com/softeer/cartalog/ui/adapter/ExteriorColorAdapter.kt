package com.softeer.cartalog.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softeer.cartalog.data.model.color.CarColor
import com.softeer.cartalog.databinding.ItemExteriorColorBinding
import com.softeer.cartalog.viewmodel.ExteriorViewModel

class ExteriorColorAdapter(private val viewModel: ExteriorViewModel) :
    RecyclerView.Adapter<ExteriorColorAdapter.ExteriorColorViewHolder>() {

    var selectedItem = viewModel.selectedColorIdx.value!!

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
        return viewModel.colorList.value?.size ?: 0
    }

    inner class ExteriorColorViewHolder(val binding: ItemExteriorColorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CarColor?, position: Int) {
            binding.viewModel = viewModel
            binding.position = position
            binding.carColor = item
            binding.adapter = this@ExteriorColorAdapter
        }
    }
}