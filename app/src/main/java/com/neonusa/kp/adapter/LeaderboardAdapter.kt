package com.neonusa.kp.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.neonusa.kp.R
import com.neonusa.kp.data.model.User
import com.neonusa.kp.databinding.ItemLeaderboardBinding
import kotlin.coroutines.coroutineContext

@SuppressLint("NotifyDataSetChanged")
class LeaderboardAdapter : RecyclerView.Adapter<LeaderboardAdapter.ViewHolder>() {
    private var data = ArrayList<User>()

    fun addItems(items: List<User>) {
        data.addAll(items)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val itemBinding: ItemLeaderboardBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: User, position: Int) {
            itemBinding.apply {

                when(position){
                    0 -> tvNo.setBackgroundColor(Color.parseColor("#fed540"))
                    1 -> tvNo.setBackgroundColor(Color.parseColor("#c2d1dd"))
                    2 -> tvNo.setBackgroundColor(Color.parseColor("#d6aa82"))

                }

                tvNo.text = (position + 1).toString()
                tvName.text = item.nama
//                imgProfile.setImageResource(item.image!!)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemLeaderboardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

}