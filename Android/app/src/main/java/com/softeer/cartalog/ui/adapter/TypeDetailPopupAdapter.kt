package com.softeer.cartalog.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softeer.cartalog.data.model.Type
import com.softeer.cartalog.databinding.ItemTypeDetailPopupBinding
import com.softeer.cartalog.viewmodel.TypeViewModel

class TypeDetailPopupAdapter(private val viewModel: TypeViewModel, private val selectedType: Int) :
    RecyclerView.Adapter<TypeDetailPopupAdapter.TrimCardViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TypeDetailPopupAdapter.TrimCardViewHolder {
        val binding =
            ItemTypeDetailPopupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrimCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrimCardViewHolder, position: Int) {
        val currentItem = viewModel.typeList.value?.get(position)
        holder.bind(currentItem, position)
    }

    override fun getItemCount(): Int {
        return 2
    }

    inner class TrimCardViewHolder(val binding: ItemTypeDetailPopupBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var isSelected = false
        fun bind(item: Type?, position: Int) {
            binding.isSelected = isSelected
            binding.viewModel = viewModel
            binding.selectedType = selectedType
            binding.position = position

            binding.btnClose.setOnClickListener {
            }
        }
    }
}