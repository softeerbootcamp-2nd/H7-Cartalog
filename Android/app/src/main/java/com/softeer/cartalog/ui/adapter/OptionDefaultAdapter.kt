package com.softeer.cartalog.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.softeer.cartalog.data.model.option.Option
import com.softeer.cartalog.databinding.ItemOptionDefaultCardBinding
import com.softeer.cartalog.ui.fragment.OptionFragmentDirections

class OptionDefaultAdapter(val list: List<Option>) :
    RecyclerView.Adapter<OptionDefaultAdapter.OptionDefaultViewHolder>() {

    var selectedItem = -1

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OptionDefaultAdapter.OptionDefaultViewHolder {
        val binding =
            ItemOptionDefaultCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OptionDefaultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OptionDefaultViewHolder, position: Int) {
        val currentItem = list[position]
        holder.bind(currentItem, position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class OptionDefaultViewHolder(val binding: ItemOptionDefaultCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Option, position: Int) {
            binding.lifecycleOwner = binding.lifecycleOwner
            binding.position = position
            binding.option = item

            binding.btnHmgData.setOnClickListener {
                val args = item.id
                val action = OptionFragmentDirections.actionOptionFragmentToOptionDetailDialog(args)
                it.findNavController().navigate(action)
            }
            binding.btnDetailView.setOnClickListener {
                val args = item.id
                val action = OptionFragmentDirections.actionOptionFragmentToOptionDetailDialog(args)
                it.findNavController().navigate(action)
            }
        }
    }
}