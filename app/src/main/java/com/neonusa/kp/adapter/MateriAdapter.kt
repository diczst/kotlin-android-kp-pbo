package com.neonusa.kp.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.neonusa.kp.Consts.BASE_URL
import com.neonusa.kp.Kotpreference
import com.neonusa.kp.data.model.Materi
import com.neonusa.kp.databinding.ItemHomeMateriBinding
import com.neonusa.kp.ui.challenge.ChallengesActivity
import com.neonusa.kp.ui.detailmateri.DetailMateriActivity
import com.neonusa.kp.ui.login.LoginActivity
import com.squareup.picasso.Picasso

@SuppressLint("NotifyDataSetChanged")
class MateriAdapter : RecyclerView.Adapter<MateriAdapter.ViewHolder>() {
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

//                this.root.setOnClickListener {
//                    if(type != "tantangan"){
//                        val intent = Intent(root.context, DetailMateriActivity::class.java)
//                        intent.putExtra(DetailMateriActivity.MATERI_ID, item.id)
//                        root.context.startActivity(intent)
//                    } else {
//                        val intent = Intent(root.context, ChallengesActivity::class.java)
//                        intent.putExtra(ChallengesActivity.MATERI_ID, item.id)
//                        root.context.startActivity(intent)
//                    }
//                }

                if(Kotpreference.level >= item.level!!){
                    tvName.alpha = 1F
                    img.alpha = 1F
                    imgLock.visibility = View.GONE

                    this.root.setOnClickListener {
                        if(type != "tantangan"){
                            val intent = Intent(root.context, DetailMateriActivity::class.java)
                            intent.putExtra(DetailMateriActivity.MATERI_ID, item.id)
                            root.context.startActivity(intent)
                        } else {
                            val intent = Intent(root.context, ChallengesActivity::class.java)
                            intent.putExtra(ChallengesActivity.MATERI_ID, item.id)
                            intent.putExtra(ChallengesActivity.MATERI_LEVEL, item.level)
                            root.context.startActivity(intent)
                        }
                    }
                } else {
                    tvName.alpha = 0.3F
                    img.alpha = 0.3F
                    imgLock.visibility = View.VISIBLE
                    this.root.setOnClickListener {
                        MaterialDialog(root.context).show {
                            title(text = "Terkunci")
                            message(text = "Selesaikan tantangan pada materi sebelumnya untuk membuka materi ini")
                            positiveButton(text = "Ok") {
                            }
                        }
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