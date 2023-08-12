package com.softeer.cartalog.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.softeer.cartalog.R
import com.softeer.cartalog.data.model.Option
import com.softeer.cartalog.databinding.ItemOptionDefaultCardBinding
import com.softeer.cartalog.viewmodel.OptionViewModel

class OptionDefaultAdapter(private val viewModel: OptionViewModel) :
    RecyclerView.Adapter<OptionDefaultAdapter.OptionDefaultViewHolder>(), OptionAdapter {

    var selectedItem = 0

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OptionDefaultAdapter.OptionDefaultViewHolder {
        val binding =
            ItemOptionDefaultCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OptionDefaultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OptionDefaultViewHolder, position: Int) {
        val currentItem = viewModel.defaultOptions?.get(position)
        holder.bind(currentItem, position)
    }

    override fun getItemCount(): Int {
        return viewModel.defaultOptions!!.size
    }

    inner class OptionDefaultViewHolder(val binding: ItemOptionDefaultCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var isSelected = false
        fun bind(item: Option?, position: Int) {
            binding.lifecycleOwner = binding.lifecycleOwner
            binding.viewModel = viewModel
            binding.position = position
            binding.optionAdapter = this@OptionDefaultAdapter
            binding.option = item

            binding.btnHmgData.setOnClickListener {
                it.findNavController().navigate(R.id.optionDetailDialog)
            }
        }
    }
}