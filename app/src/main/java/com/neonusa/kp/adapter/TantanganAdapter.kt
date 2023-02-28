package com.neonusa.kp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.neonusa.kp.data.model.Tantangan
import com.neonusa.kp.databinding.ItemChallengeBinding

class TantanganAdapter : RecyclerView.Adapter<TantanganAdapter.ViewHolder>() {

    private var data = ArrayList<Tantangan>()

    fun addItems(items: List<Tantangan>) {
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val itemBinding: ItemChallengeBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: Tantangan, position: Int) {
            itemBinding.apply {
                tvName.text = item.nama
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TantanganAdapter.ViewHolder {
        return ViewHolder(
            ItemChallengeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false)
        )
    }

    override fun onBindViewHolder(holder: TantanganAdapter.ViewHolder, position: Int) {
        holder.bind(data[position], position)
    }

    override fun getItemCount(): Int = data.size
}