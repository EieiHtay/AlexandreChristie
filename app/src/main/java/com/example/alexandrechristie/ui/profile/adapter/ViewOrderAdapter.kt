package com.example.alexandrechristie.ui.profile.adapter

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.alexandrechristie.data.entity.ViewOrder
import com.example.alexandrechristie.databinding.ItemViewOrderBinding

class ViewOrderAdapter(
    private var viewOrderList: ArrayList<ViewOrder>
): RecyclerView.Adapter<ViewOrderAdapter.ViewOrderHolder>()  {

    private var total : Int=0
    private var subTotal:Int=0
    fun setData(viewOrderList: ArrayList<ViewOrder>){
        this.viewOrderList=viewOrderList
        notifyDataSetChanged()
    }

    inner class ViewOrderHolder(private val binding: ItemViewOrderBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(viewOrder: ViewOrder){
            binding.codeNo.text=viewOrder.watchCode
            binding.orderViewPrice.text=viewOrder.price.toString()
            binding.viewOrderImg.setImageURI(Uri.parse(viewOrder.orderImg))
            binding.orderViewQty.text=viewOrder.qty.toString()
            binding.orderDate.text=viewOrder.orderDate

            total=viewOrder.qty * viewOrder.price

            subTotal+=total

            binding.orderViewTotal.text=total.toString()

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewOrderHolder {
        return ViewOrderHolder(
            ItemViewOrderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewOrderHolder, position: Int) {
        holder.bind(viewOrderList[position])
    }

    override fun getItemCount(): Int {
//        Log.i("get item", "getItemCount: ${viewOrderList.size}")
        return viewOrderList.size
    }
}