package com.softeer.cartalog.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softeer.cartalog.databinding.ItemTrimCardBinding
import com.softeer.cartalog.model.data.Trim

class TrimCardAdapter(private val trimList: List<Trim>) : RecyclerView.Adapter<TrimCardAdapter.TrimCardViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrimCardAdapter.TrimCardViewHolder {
        val binding = ItemTrimCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrimCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrimCardViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return trimList.size
    }

    inner class TrimCardViewHolder(binding: ItemTrimCardBinding):
        RecyclerView.ViewHolder(binding.root) {
            fun bind() {

            }
    }
}