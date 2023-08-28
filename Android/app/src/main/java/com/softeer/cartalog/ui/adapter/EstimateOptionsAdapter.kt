package com.softeer.cartalog.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softeer.cartalog.data.model.estimate.SimilarEstimateOption
import com.softeer.cartalog.data.model.option.Option
import com.softeer.cartalog.databinding.ItemEstimateOptionBinding
import com.softeer.cartalog.viewmodel.EstimateViewModel

class EstimateOptionsAdapter(private val viewModel: EstimateViewModel, private val cardPosition: Int) :RecyclerView.Adapter<EstimateOptionsAdapter.EstimateOptionsViewHolder>() {

    var selectedItems = mutableListOf<SimilarEstimateOption>()
    val items = mutableListOf<SimilarEstimateOption>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EstimateOptionsAdapter.EstimateOptionsViewHolder {
        val binding =
            ItemEstimateOptionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EstimateOptionsViewHolder(binding)
    }

    fun setItems(){
        items.clear()
        items.addAll(viewModel.estimateList.value!![cardPosition].nonExistentOptions)
    }

    override fun onBindViewHolder(holder: EstimateOptionsViewHolder, position: Int) {
        val currentItem = items[position]
        holder.bind(currentItem, position)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class EstimateOptionsViewHolder(val binding: ItemEstimateOptionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SimilarEstimateOption, position: Int) {

            binding.adapter = this@EstimateOptionsAdapter
            binding.viewModel = viewModel
            binding.option = item

        }
    }
}