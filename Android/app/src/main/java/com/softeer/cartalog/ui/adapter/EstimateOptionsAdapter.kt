package com.softeer.cartalog.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softeer.cartalog.data.model.color.CarColor
import com.softeer.cartalog.databinding.ItemEstimateOptionBinding
import com.softeer.cartalog.viewmodel.EstimateViewModel

class EstimateOptionsAdapter(private val viewModel: EstimateViewModel) :RecyclerView.Adapter<EstimateOptionsAdapter.EstimateOptionsViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EstimateOptionsAdapter.EstimateOptionsViewHolder {
        val binding =
            ItemEstimateOptionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EstimateOptionsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EstimateOptionsViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
        return 0
    }

    inner class EstimateOptionsViewHolder(val binding: ItemEstimateOptionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CarColor?, position: Int) {
            binding.lifecycleOwner = binding.lifecycleOwner
        }
    }
}