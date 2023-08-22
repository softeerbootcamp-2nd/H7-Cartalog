package com.softeer.cartalog.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.softeer.cartalog.data.model.Option
import com.softeer.cartalog.databinding.ItemOptionSelectCardBinding
import com.softeer.cartalog.ui.fragment.OptionFragmentDirections
import com.softeer.cartalog.viewmodel.OptionViewModel

class OptionSelectAdapter(private val viewModel: OptionViewModel, private val filterParam: String) :
    RecyclerView.Adapter<OptionSelectAdapter.OptionSelectViewHolder>() {

    var selectedItems = mutableListOf<Option>()
    val items = mutableListOf<Option>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OptionSelectAdapter.OptionSelectViewHolder {
        val binding =
            ItemOptionSelectCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OptionSelectViewHolder(binding)
    }

    fun setItems(options: List<Option>){
        items.clear()
        if(filterParam == "전체"){
            items.addAll(options)
        }else{
            items.addAll(options.filter {
                it.parentCategory?.contains(filterParam) ?: false
            })
        }
    }

    override fun onBindViewHolder(holder: OptionSelectViewHolder, position: Int) {
        val currentItem = items[position]
        holder.bind(currentItem, position)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class OptionSelectViewHolder(val binding: ItemOptionSelectCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Option?, position: Int) {
            binding.lifecycleOwner = binding.lifecycleOwner
            binding.viewModel = viewModel
            binding.position = position
            binding.optionAdapter = this@OptionSelectAdapter

            item?.let { option ->
                binding.option = option
                binding.btnHmgData.setOnClickListener {
                    val args = option.id
                    val action =
                        OptionFragmentDirections.actionOptionFragmentToOptionDetailDialog(args)
                    it.findNavController().navigate(action)
                }
                binding.btnDetailView.setOnClickListener {
                    val args = option.id
                    val action =
                        OptionFragmentDirections.actionOptionFragmentToOptionDetailDialog(args)
                    it.findNavController().navigate(action)
                }
            }
        }
    }


}