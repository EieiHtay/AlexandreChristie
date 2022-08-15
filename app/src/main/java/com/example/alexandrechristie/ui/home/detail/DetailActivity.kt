package com.example.alexandrechristie.ui.home.detail

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.alexandrechristie.MainActivity
import com.example.alexandrechristie.data.entity.Watch
import com.example.alexandrechristie.data.entity.WatchOrder
import com.example.alexandrechristie.databinding.ActivityDetailBinding
import com.example.alexandrechristie.ui.home.order.OrderActivity
import com.example.alexandrechristie.viewModel.OrderViewModel
import com.example.alexandrechristie.viewModel.WatchViewModel

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    private var qty : Int = 0
    private val viewModel: WatchViewModel by viewModels()
    private val orderViewModel : OrderViewModel by viewModels()
    private var mProfileUri: Uri?=null
    private var watchId:Int ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        watchId = intent.getIntExtra("id",0)
        getWatch(watchId!!)

        binding.imgBack.setOnClickListener{
            val intent = Intent(this@DetailActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.plus.setOnClickListener{
            qty=++qty
            binding.count.text= qty.toString()
        }

        binding.minus.setOnClickListener{
            if (binding.count.text != "0"){
                qty=--qty
                binding.count.text=qty.toString()
            }
        }

        binding.order.setOnClickListener{
            if(qty == 0){
                Toast.makeText(this,"Please select the quantity of food.", Toast.LENGTH_SHORT).show()
            }
            else {
//                startActivity(Intent(this,LoginActivity::class.java))
                if(mProfileUri != null) {
                    upsertOrder()
                }

            }
            finish()
        }

    }

    private fun upsertOrder() {
//        val foodId = binding
        val codeNo=binding.codeNo.text.toString().trim()
        val price=binding.price.text.toString().trim()
        val qty = qty.toString().trim()

        val watchOrder=WatchOrder(
            null, watchId!!,codeNo, price.toInt(),qty.toInt(), mProfileUri.toString()
        )
        orderViewModel.insertWatchOrderForm(watchOrder)
        val intent = Intent(this, OrderActivity::class.java)
        intent.putExtra("qty", qty)
        intent.putExtra("id",watchId)
        startActivity(intent)
    }

    private fun getWatch(id: Int) {
        viewModel.getWatchById(id).observe(this){
            setUpData(it!!)
        }
    }

    private fun setUpData(it: Watch) {
        binding.apply {
            codeNo.setText(it.code)
            price.setText(it.price.toString())
            watchDesc.setText(it.description)
            mProfileUri = Uri.parse(it.img)
            detailImg.setImageURI(Uri.parse(it.img))
            category.setText(it.category)
        }
    }
}