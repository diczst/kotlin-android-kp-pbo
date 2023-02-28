package com.neonusa.kp.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.neonusa.kp.data.model.Materi
import com.neonusa.kp.databinding.ItemHomeMateriBinding
import com.neonusa.kp.ui.challenge.ChallengesActivity
import com.neonusa.kp.ui.detailmateri.DetailMateriActivity
import com.squareup.picasso.Picasso

@SuppressLint("NotifyDataSetChanged")
class MateriAdapter : RecyclerView.Adapter<MateriAdapter.ViewHolder>() {
    private val BASE_URL = "http://192.168.43.181/pebeo/public" // ini kalau pakai hotspot euler
//    private val BASE_URL = "http://10.102.14.17/pebeo/public" // ini kalau pakai wifi unib

    private val USER_URL = "$BASE_URL/storage/user/"

    private var data = ArrayList<Materi>()
    var type = ""

    fun addItems(items: List<Materi>) {
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val itemBinding: ItemHomeMateriBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: Materi, position: Int) {
            itemBinding.apply {

                tvName.text = item.nama

                if(item.image != null){
                    if(type == "tantangan"){
                        Picasso.get().load(USER_URL + item.image_tantangan).into(img)
                    } else {
                        Picasso.get().load(USER_URL + item.image).into(img)
                    }
                }

                if(type != "tantangan"){
                    this.root.setOnClickListener{
                        val intent = Intent(root.context, DetailMateriActivity::class.java)
                        intent.putExtra(DetailMateriActivity.MATERI_ID, item.id)
                        root.context.startActivity(intent)
                    }
                } else {

                    this.root.setOnClickListener {
                        val intent = Intent(root.context, ChallengesActivity::class.java)
                        intent.putExtra(ChallengesActivity.MATERI_ID, item.id)
                        root.context.startActivity(intent)
                    }
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHomeMateriBinding.inflate(
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