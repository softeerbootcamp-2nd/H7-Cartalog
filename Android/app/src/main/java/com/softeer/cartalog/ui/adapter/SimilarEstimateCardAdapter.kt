package com.softeer.cartalog.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softeer.cartalog.data.model.estimate.SimilarEstimateOption
import com.softeer.cartalog.data.model.estimate.SimilarEstimates
import com.softeer.cartalog.data.model.trim.Trim
import com.softeer.cartalog.databinding.ItemEstimateCardBinding
import com.softeer.cartalog.databinding.ItemTrimCardBinding
import com.softeer.cartalog.viewmodel.EstimateViewModel
import com.softeer.cartalog.viewmodel.TrimViewModel

class SimilarEstimateCardAdapter(private val viewModel: EstimateViewModel) :
    RecyclerView.Adapter<SimilarEstimateCardAdapter.SimilarEstimateCardViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SimilarEstimateCardAdapter.SimilarEstimateCardViewHolder {
        val binding =
            ItemEstimateCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SimilarEstimateCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SimilarEstimateCardViewHolder, position: Int) {
        val currentItem = viewModel.estimateList.value?.get(position)
        holder.bind(currentItem, position)
    }

    override fun getItemCount(): Int {
        return viewModel.estimateList.value!!.size
    }

    inner class SimilarEstimateCardViewHolder(val binding: ItemEstimateCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var isSelected = false
        fun bind(item: SimilarEstimates?, position: Int) {
            isSelected = position == viewModel.selectedCard.value
            binding.isSelected = isSelected
            binding.item = item
        }
    }
}