package com.softeer.cartalog.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softeer.cartalog.databinding.ItemTrimCardBinding
import com.softeer.cartalog.model.data.Trim
import com.softeer.cartalog.viewmodel.TrimViewModel

class TrimCardAdapter(private val viewModel: TrimViewModel) : RecyclerView.Adapter<TrimCardAdapter.TrimCardViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrimCardAdapter.TrimCardViewHolder {
        val binding = ItemTrimCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrimCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrimCardViewHolder, position: Int) {
        val currentItem = viewModel.trimList.value?.get(position)
        holder.bind(currentItem, position)
    }

    override fun getItemCount(): Int {
        return viewModel.trimList.value!!.size
    }

    inner class TrimCardViewHolder(val binding: ItemTrimCardBinding):
        RecyclerView.ViewHolder(binding.root) {
            var isSelected = false
            fun bind(item: Trim?, position: Int) {
                isSelected = position == viewModel.selectedTrim.value
                binding.isSelected = isSelected
                binding.trimItem = item
            }
    }
}