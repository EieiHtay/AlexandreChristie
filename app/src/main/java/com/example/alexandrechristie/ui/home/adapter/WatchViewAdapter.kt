package com.example.alexandrechristie.ui.home.adapter

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.alexandrechristie.data.entity.Watch
import com.example.alexandrechristie.databinding.ItemViewBinding
import com.example.alexandrechristie.ui.home.detail.DetailActivity

class WatchViewAdapter(
    private var watchViewList: ArrayList<Watch>
): RecyclerView.Adapter<WatchViewAdapter.WatchViewHolder>()  {
    fun setData(watchViewList: ArrayList<Watch>){
        this.watchViewList=watchViewList
        notifyDataSetChanged()
    }

    inner class WatchViewHolder(private val binding: ItemViewBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(watch: Watch){
            binding.codeNo.text=watch.code
            binding.price.text=watch.price.toString()
            binding.imgItem.setImageURI(Uri.parse(watch.img))

            binding.root.setOnClickListener{
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra("id",watch.id)
                itemView.context.startActivity(intent)
            }

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchViewHolder {
        return WatchViewHolder(
            ItemViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: WatchViewHolder, position: Int) {
        holder.bind(watchViewList[position])
    }

    override fun getItemCount(): Int {
        Log.i("get item", "getItemCount: ${watchViewList.size}")
        return watchViewList.size
    }
}