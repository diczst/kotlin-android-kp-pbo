package com.neonusa.kp.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.neonusa.kp.Kotpreference
import com.neonusa.kp.data.model.Siswa
import com.neonusa.kp.databinding.ItemLeaderboardBinding
import com.neonusa.kp.ui.leaderboard.LeaderboardDetailActivity
import com.squareup.picasso.Picasso

@SuppressLint("NotifyDataSetChanged")
class LeaderboardAdapter : RecyclerView.Adapter<LeaderboardAdapter.ViewHolder>() {
    private val BASE_URL = "http://192.168.43.181/pebeo/public" // ini kalau pakai hotspot euler
//    private val BASE_URL = "http://10.102.14.17/pebeo/public" // ini kalau pakai wifi unib

    private val USER_URL = "$BASE_URL/storage/user/"

    private var data = ArrayList<Siswa>()

    fun addItems(items: List<Siswa>) {
        data.addAll(items)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val itemBinding: ItemLeaderboardBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: Siswa, position: Int) {
            itemBinding.apply {

                when(position){
                    0 -> tvNo.setBackgroundColor(Color.parseColor("#fed540"))
                    1 -> tvNo.setBackgroundColor(Color.parseColor("#c2d1dd"))
                    2 -> tvNo.setBackgroundColor(Color.parseColor("#d6aa82"))
                }

                if(item.id == Kotpreference.getUser()?.id){
                    itemLayout.setBackgroundColor(Color.parseColor("#F7F7F7"))
                    tvName.setTextColor(Color.parseColor("#1cb0f6"))
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

                if(item.exp?.toInt()!! < 300){
                    tvRank.text = "Perunggu"
                }

                if(item.exp?.toInt() in 300..700){
                    tvRank.text = "Perak"
                }

                if(item.exp?.toInt() in 701..1000){
                    tvRank.text = "Emas"
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

}