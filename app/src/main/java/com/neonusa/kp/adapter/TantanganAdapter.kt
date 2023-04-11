package com.neonusa.kp.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.neonusa.kp.data.model.Tantangan
import com.neonusa.kp.databinding.ItemChallengeBinding
import com.neonusa.kp.ui.challenge.ChallengeActivity


class TantanganAdapter : RecyclerView.Adapter<TantanganAdapter.ViewHolder>() {

    private var data = ArrayList<Tantangan>()
    var materiLevel = 0
    var userTantanganLevel = 0
    var userMateriLevel = 0


    fun addItems(items: List<Tantangan>) {
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val itemBinding: ItemChallengeBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: Tantangan, position: Int) {
            itemBinding.apply {
                tvName.text = item.nama
                if(userTantanganLevel >= item.level!! || (userMateriLevel > materiLevel)){
                    layoutMain.alpha = 1F
                    this.root.setOnClickListener {
                        val intent = Intent(root.context, ChallengeActivity::class.java)
                        intent.putExtra(ChallengeActivity.TANTANGAN_ID, item.id)
                        intent.putExtra(ChallengeActivity.MATERI_LEVEL, materiLevel)
                        intent.putExtra(ChallengeActivity.TANTANGAN_TOTAL, data.size)
                        root.context.startActivity(intent)
                        (root.context as Activity).finish()
                    }
                } else {
                    layoutMain.alpha = 0.3F
                    this.root.setOnClickListener {
                        MaterialDialog(root.context).show {
                            title(text = "Terkunci")
                            message(text = "Selesaikan tantangan sebelumnya untuk membuka tantangan ini")
                            positiveButton(text = "Ok") {
                            }
                        }
                    }
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