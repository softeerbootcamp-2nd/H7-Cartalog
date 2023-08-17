package com.softeer.cartalog.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softeer.cartalog.data.model.SummaryOptionPrice
import com.softeer.cartalog.databinding.ItemPriceSummaryOptionBinding

class SummaryOptionsAdapter(private val optionList: List<SummaryOptionPrice>) :
    RecyclerView.Adapter<SummaryOptionsAdapter.SummaryOptionsViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SummaryOptionsAdapter.SummaryOptionsViewHolder {
        val binding = ItemPriceSummaryOptionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SummaryOptionsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return optionList.size
    }

    override fun onBindViewHolder(holder: SummaryOptionsViewHolder, position: Int) {
        val currentItem = optionList[position]
        holder.bind(currentItem)
    }

    inner class SummaryOptionsViewHolder(val binding: ItemPriceSummaryOptionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SummaryOptionPrice) {
            binding.option = item
        }
    }
}