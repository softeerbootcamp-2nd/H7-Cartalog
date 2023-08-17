package com.softeer.cartalog.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softeer.cartalog.data.model.CarColor
import com.softeer.cartalog.databinding.ItemInteriorColorBinding
import com.softeer.cartalog.viewmodel.InteriorViewModel

class InteriorColorAdapter(private val viewModel: InteriorViewModel) :
    RecyclerView.Adapter<InteriorColorAdapter.InteriorColorViewHolder>() {

    var selectedItem = 0

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InteriorColorAdapter.InteriorColorViewHolder {
        val binding =
            ItemInteriorColorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InteriorColorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InteriorColorViewHolder, position: Int) {
        val currentItem = viewModel.colorList.value?.get(position)
        holder.bind(currentItem, position)
    }

    override fun getItemCount(): Int {
        return viewModel.colorList.value?.size ?: 0
    }

    inner class InteriorColorViewHolder(val binding: ItemInteriorColorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CarColor?, position: Int) {
            binding.lifecycleOwner = binding.lifecycleOwner
            binding.viewModel = viewModel
            binding.position = position
            binding.carColor = item
            binding.adapter = this@InteriorColorAdapter
        }
    }
}