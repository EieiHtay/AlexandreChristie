package com.example.alexandrechristie.ui.home.ordersuccess

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.alexandrechristie.MainActivity
import com.example.alexandrechristie.R
import com.example.alexandrechristie.databinding.ActivityOrderSuccessBinding

class OrderSuccessActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderSuccessBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityOrderSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backList.setOnClickListener{
            startActivity(Intent(this@OrderSuccessActivity,MainActivity::class.java))
        }
        binding.backArrow.setOnClickListener{
            startActivity(Intent(this@OrderSuccessActivity,MainActivity::class.java))
        }
    }
}