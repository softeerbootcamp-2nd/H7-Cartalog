package com.softeer.cartalog.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softeer.cartalog.data.model.ConfirmDetail
import com.softeer.cartalog.databinding.ItemConfirmDetailContainerBinding

class ConfirmDetailTitleAdapter(private val detailTitleList: List<ConfirmDetail>) :
    RecyclerView.Adapter<ConfirmDetailTitleAdapter.ConfirmDetailTitleViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ConfirmDetailTitleViewHolder {
        val binding = ItemConfirmDetailContainerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ConfirmDetailTitleViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return detailTitleList.size
    }

    override fun onBindViewHolder(holder: ConfirmDetailTitleViewHolder, position: Int) {
        val currentItem = detailTitleList[position]
        holder.bind(currentItem)
    }

    inner class ConfirmDetailTitleViewHolder(val binding: ItemConfirmDetailContainerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ConfirmDetail) {
            binding.detailData = item
        }
    }
}