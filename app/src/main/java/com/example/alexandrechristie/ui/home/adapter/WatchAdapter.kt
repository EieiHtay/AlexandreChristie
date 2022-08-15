package com.example.alexandrechristie.ui.home.adapter

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.alexandrechristie.data.entity.Watch
import com.example.alexandrechristie.databinding.ItemWatchBinding
import com.example.alexandrechristie.ui.insertData.WatchActivity

class WatchAdapter(
    private var watchList: ArrayList<Watch>
): RecyclerView.Adapter<WatchAdapter.WatchHolder>()  {

    fun setData(watchList: ArrayList<Watch>){
        this.watchList=watchList
        notifyDataSetChanged()
    }

    inner class WatchHolder(private val binding: ItemWatchBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(watch: Watch) {

            binding.codeNo.text = watch.code
            binding.price.text = watch.price.toString()
            binding.imgItem.setImageURI(Uri.parse(watch.img))

            binding.imgBtnEdit.setOnClickListener {
                val intent = Intent(itemView.context, WatchActivity::class.java)
                intent.putExtra("Edit",true)
                intent.putExtra("id",watch.id)
                itemView.context.startActivity(intent)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchHolder {
        return WatchHolder(
            ItemWatchBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }
    override fun onBindViewHolder(holder: WatchHolder, position: Int) {
        holder.bind(watchList[position])
    }

    override fun getItemCount(): Int {
        Log.i("watch", "getItemCount: ${watchList.size}")
        return watchList.size
    }


}