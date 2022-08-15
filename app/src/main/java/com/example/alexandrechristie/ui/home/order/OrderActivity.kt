package com.example.alexandrechristie.ui.home.order

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alexandrechristie.MainActivity
import com.example.alexandrechristie.data.entity.UserLogin
import com.example.alexandrechristie.data.entity.ViewOrder
import com.example.alexandrechristie.data.entity.WatchOrderResponse
import com.example.alexandrechristie.databinding.ActivityOrderBinding
import com.example.alexandrechristie.ui.home.order.adapter.OrderAdapter
import com.example.alexandrechristie.ui.home.ordersuccess.OrderSuccessActivity
import com.example.alexandrechristie.viewModel.OrderViewModel
import com.example.alexandrechristie.viewModel.UserLoginViewModel
import com.example.alexandrechristie.viewModel.ViewOrderViewModel
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList

class OrderActivity : AppCompatActivity(),OrderAdapter.OrderInterface {

    private lateinit var binding: ActivityOrderBinding
    private lateinit var orderAdapter: OrderAdapter
    private lateinit var orderList : ArrayList<WatchOrderResponse>
    private lateinit var userLoginList : ArrayList<UserLogin>
    private val viewModel : OrderViewModel by viewModels()
    private val viewOrderViewModel : ViewOrderViewModel by viewModels()
    private val userViewModel : UserLoginViewModel by viewModels()

    private var id : Int ?= null
    private var watchId : Int = 0
    private lateinit var watchCode : String
    private var mProfileUri: Uri?=null

    private lateinit var userName : String
    private lateinit var userPhone : String
    private lateinit var userEmail : String

    private var price : Int= 0
    private var qty :Int=0
    private var total : Int = 0
    private var deliveryFee : Int = 3
    private var subtotal : Int = 0
    private var finaltotal : Int =0
    var orderDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().time)!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.deliveryFee.text=deliveryFee.toString()

        orderList = arrayListOf()
        initRec()
        getWatchOrder("")
        getUserLogin()
        onClick()
    }

    private fun onClick() {
        binding.addItem.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
            Toast.makeText(this, "${orderDate}", Toast.LENGTH_SHORT).show()
            finish()
        }
        binding.checkout.setOnClickListener{
            getWatchOrder("")

            upsertViewOrder()
            deleteAllOrder()
            Toast.makeText(this, "Order Successfully", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, OrderSuccessActivity::class.java))
            finish()
        }
        binding.cancel.setOnClickListener{
            deleteAllOrder()
            Toast.makeText(this, "Order Cancel Successful", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

    }

    private fun upsertViewOrder() {
        val viewOrderWatchId = watchId
        val viewOrderWatchCode=watchCode.trim()
        val viewOrderPrice=price
        val viewOrderQty=qty
        val viewOrderTotal = price * qty
        val uName = userName.trim()
        val uPhone = userPhone.trim()
        val uEmail = userEmail.trim()

        val viewOrder= ViewOrder(
            id,viewOrderWatchId,viewOrderWatchCode, viewOrderPrice,viewOrderQty,viewOrderTotal,mProfileUri.toString(), uName ,uPhone, uEmail,orderDate
        )
        viewOrderViewModel.insertViewOrderForm(viewOrder)
    }

    private fun deleteAllOrder() {
        viewModel.deleteAllOrder()
    }

    private fun getWatchOrder(s: String) {
        viewModel.getWatchOrder(s).observe(this){
            Log.i("watch order", "setupSearch: $it")
            if(it.isNotEmpty()) {
                orderList = it as ArrayList<WatchOrderResponse>
                orderAdapter.setData(orderList)
                for (order in orderList){
                    watchId = order.watchId
                    watchCode=order.watchCode
//                    viewOrderImg = order.orderImg
                    mProfileUri= Uri.parse(order.orderImg)

                    price=order.price
                    qty=order.totalQtyPerFoodId
                    total=price*qty
                    subtotal+=total
                    finaltotal=subtotal+deliveryFee

                    binding.subTotal.text=subtotal.toString()
                    binding.finalTotal.text=finaltotal.toString()

                    upsertViewOrder()
                }
            }
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun getUserLogin() {
        userViewModel.getUserLogin().observe(this){
            if(it.isNotEmpty()) {
                userLoginList = it as ArrayList<UserLogin>
                for (userLogin in userLoginList){
                    userName=userLogin.userName
                    userPhone=userLogin.phone
                    userEmail=userLogin.email
                }
            }
        }
    }
    private fun initRec() {
        orderAdapter = OrderAdapter(orderList,this)
        binding.recOrderList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@OrderActivity)
            adapter = orderAdapter
        }
    }

    override fun onDeletedOrder(watchId: Int,index : Int) {
        viewModel.deleteOrder(watchId)
    }

}