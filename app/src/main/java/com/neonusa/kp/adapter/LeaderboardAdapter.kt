package com.neonusa.kp.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.neonusa.kp.Consts.BASE_URL
import com.neonusa.kp.Kotpreference
import com.neonusa.kp.R
import com.neonusa.kp.data.model.Siswa
import com.neonusa.kp.databinding.ItemLeaderboardBinding
import com.neonusa.kp.ui.leaderboard.LeaderboardDetailActivity
import com.squareup.picasso.Picasso

@SuppressLint("NotifyDataSetChanged")
class LeaderboardAdapter : RecyclerView.Adapter<LeaderboardAdapter.ViewHolder>() {
    private val USER_URL = "$BASE_URL/storage/user/"

    private var data = ArrayList<Siswa>()
    var currentPosition = 0

    fun addItems(items: List<Siswa>) {
        data.addAll(items)
        notifyDataSetChanged()
    }

    fun getCurrentPos(items:List<Siswa>){
        for ((index, value) in items.withIndex()){
            if(value.id == Kotpreference.getUser()?.id){
                currentPosition = index + 1
            }
        }
    }

    inner class ViewHolder(private val itemBinding: ItemLeaderboardBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: Siswa, position: Int) {
            itemBinding.apply {

                when(position){
                    0 -> {
                        tvNo.setBackgroundColor(Color.parseColor("#fed540"))
                        tvNo.setTextColor(Color.WHITE)
                    }
                    1 -> {
                        tvNo.setBackgroundColor(Color.parseColor("#c2d1dd"))
                        tvNo.setTextColor(Color.WHITE)
                    }
                    2 -> {
                        tvNo.setBackgroundColor(Color.parseColor("#d6aa82"))
                        tvNo.setTextColor(Color.WHITE)
                    }
                }

                if(item.id == Kotpreference.getUser()?.id){
                    itemLayout.setBackgroundColor(Color.parseColor("#C0EBFF"))
                    tvName.setTextColor(Color.parseColor("#1899d6"))
                } else {
                    this.root.setOnClickListener {
                        val intent = Intent(root.context, LeaderboardDetailActivity::class.java)
                        intent.putExtra(LeaderboardDetailActivity.USER_ID, item.id)
                        root.context.startActivity(intent)
                    }
                }

                tvNo.text = (position + 1).toString()

                if(item.image != null){
                    Picasso.get().load(USER_URL + item.image).into(imgProfile)
                }

                tvName.text = item.nama_lengkap

                if(item.exp!! < 300){
                    tvRank.text = root.context.getString(R.string.perunggu)
                }

                if(item.exp in 300..700){
                    tvRank.text = root.context.getString(R.string.perak)
                }

                if(item.exp in 701..1000){
                    tvRank.text = root.context.getString(R.string.emas)
                }

                tvExp.text = "Exp. ${item.exp}"
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

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

}