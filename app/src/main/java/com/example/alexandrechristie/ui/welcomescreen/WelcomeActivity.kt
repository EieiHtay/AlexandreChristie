package com.example.alexandrechristie.ui.welcomescreen

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.alexandrechristie.MainActivity
import com.example.alexandrechristie.R
import com.example.alexandrechristie.data.entity.User
import com.example.alexandrechristie.data.entity.UserLogin
import com.example.alexandrechristie.databinding.ActivityWelcomeBinding
import com.example.alexandrechristie.ui.login.LoginActivity
import com.example.alexandrechristie.ui.register.RegisterActivity
import com.example.alexandrechristie.viewModel.OrderViewModel
import com.example.alexandrechristie.viewModel.UserLoginViewModel

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding
    private val viewModel : UserLoginViewModel by viewModels()
    private lateinit var userLoginList: ArrayList<UserLogin>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewItem.setOnClickListener{
            getUserLoginData()
//            startActivity(Intent(this, LoginActivity::class.java))
//            finish()
        }
        binding.adminPanel.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
//            finish()
        }
    }
    private fun getUserLoginData(){
        viewModel.getUserLogin().observe(this) setOnClickListener@{
            userLoginList=it as ArrayList<UserLogin>
            if (userLoginList.isEmpty()){
                startActivity(Intent(this@WelcomeActivity, LoginActivity::class.java))
                finish()
            }else{
                startActivity(Intent(this@WelcomeActivity,MainActivity::class.java))
            }

        }
    }
}