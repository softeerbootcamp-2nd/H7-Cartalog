package com.softeer.cartalog.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softeer.cartalog.data.model.db.PriceData
import com.softeer.cartalog.databinding.ItemConfirmDetailContentBinding

class ConfirmDetailContentsAdapter(private val detailContentsList: List<PriceData>): RecyclerView.Adapter<ConfirmDetailContentsAdapter.ConfirmDetailContentsViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ConfirmDetailContentsViewHolder {
        val binding = ItemConfirmDetailContentBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ConfirmDetailContentsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return detailContentsList.size
    }

    override fun onBindViewHolder(holder: ConfirmDetailContentsViewHolder, position: Int) {
        val currentItem = detailContentsList[position]
        holder.bind(currentItem)
    }

    inner class ConfirmDetailContentsViewHolder(val binding: ItemConfirmDetailContentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PriceData) {
            binding.item = item
        }
    }
}