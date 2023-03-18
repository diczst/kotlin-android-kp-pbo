package com.neonusa.kp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.neonusa.kp.data.model.Tantangan
import com.neonusa.kp.databinding.ItemChallengeBinding
import com.neonusa.kp.ui.challenge.ChallengeActivity
import com.neonusa.kp.ui.leaderboard.LeaderboardDetailActivity

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
                this.root.setOnClickListener {
                    val intent = Intent(root.context, ChallengeActivity::class.java)
                    intent.putExtra(ChallengeActivity.TANTANGAN_ID, item.id)
                    root.context.startActivity(intent)
                }
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