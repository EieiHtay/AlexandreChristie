package com.example.alexandrechristie.ui.home.order.adapter

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.alexandrechristie.data.entity.WatchOrderResponse
import com.example.alexandrechristie.databinding.ItemOrderBinding
import com.example.alexandrechristie.viewModel.OrderViewModel

class OrderAdapter(
    private var orderList: ArrayList<WatchOrderResponse>,
    private val listener : OrderInterface
): RecyclerView.Adapter<OrderAdapter.OrderHolder>() {
    private var total : Int = 0
    private var qty : Int = 0
    private var price : Int = 0
    private var id : Int ?=null
    private lateinit var watchCode : String
//    private lateinit var listener: ItemListener

    interface OrderInterface{
        fun onDeletedOrder(watchId: Int,index: Int)
    }

    fun setData(orderList: ArrayList<WatchOrderResponse>){
        this.orderList=orderList
        notifyDataSetChanged()
    }

    inner class OrderHolder(private val binding: ItemOrderBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(order: WatchOrderResponse,index: Int){
            watchCode=order.watchCode
            price = order.price
            qty = order.totalQtyPerFoodId
            id=order.watchId

            binding.codeNo.text=order.watchCode
            binding.orderPrice.text=price.toString()
            binding.orderImg.setImageURI(Uri.parse(order.orderImg))
            binding.qty.text = order.totalQtyPerFoodId.toString()

            fun multiply(x: Int, y: Int): Int {
                return  x * y
            }

            total=multiply(price,qty)

            binding.total.text = total.toString()
//            id=watchOrder.watchId

            binding.clear.setOnClickListener {
//                if (watchCode.equals(binding.codeNo.text)){
                    deleteOrder(id!!,index)

//                }
            }

        }
    }

    private fun deleteOrder(watchId: Int, index:Int) {
            listener.onDeletedOrder(watchId,index)
//            orderList.removeAt(index)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHolder {
        return OrderHolder(
            ItemOrderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: OrderHolder, position: Int) {
        holder.bind(orderList[position],position)
    }

    override fun getItemCount(): Int {
        Log.i("order list", "getItemCount: ${orderList.size}")
        return orderList.size
    }
}