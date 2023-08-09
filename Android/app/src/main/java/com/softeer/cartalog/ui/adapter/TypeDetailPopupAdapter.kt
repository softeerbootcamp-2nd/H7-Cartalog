package com.softeer.cartalog.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softeer.cartalog.databinding.ItemTypeDetailPopupBinding
import com.softeer.cartalog.data.model.Type
import com.softeer.cartalog.ui.dialog.TypeDetailPopupActivity
import com.softeer.cartalog.viewmodel.TypeViewModel

class TypeDetailPopupAdapter(private val viewModel: TypeViewModel) :
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
        return viewModel.typeList.value!!.size
    }

    inner class TrimCardViewHolder(val binding: ItemTypeDetailPopupBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var isSelected = false
        fun bind(item: Type?, position: Int) {
            isSelected = position == viewModel.selectedType.value
            binding.isSelected = isSelected
            binding.typeItem = item
            binding.activity = binding.root.context as TypeDetailPopupActivity
        }
    }
}