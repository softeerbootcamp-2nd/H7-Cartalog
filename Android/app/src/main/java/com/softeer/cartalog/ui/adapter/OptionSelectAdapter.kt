package com.softeer.cartalog.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softeer.cartalog.data.model.Option
import com.softeer.cartalog.databinding.ItemOptionSelectCardBinding
import com.softeer.cartalog.viewmodel.OptionViewModel

class OptionSelectAdapter(private val viewModel: OptionViewModel) :
    RecyclerView.Adapter<OptionSelectAdapter.OptionSelectViewHolder>(), OptionAdapter {

    var selectedItem = 0

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OptionSelectAdapter.OptionSelectViewHolder {
        val binding =
            ItemOptionSelectCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OptionSelectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OptionSelectViewHolder, position: Int) {
        val currentItem = viewModel.selectOptions?.get(position)
        holder.bind(currentItem, position)
    }

    override fun getItemCount(): Int {
        return viewModel.selectOptions!!.size
    }

    inner class OptionSelectViewHolder(val binding: ItemOptionSelectCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var isSelected = false
        fun bind(item: Option?, position: Int) {
            binding.lifecycleOwner = binding.lifecycleOwner
            binding.viewModel = viewModel
            binding.position = position
            binding.optionAdapter = this@OptionSelectAdapter
            binding.option = item
        }
    }
}