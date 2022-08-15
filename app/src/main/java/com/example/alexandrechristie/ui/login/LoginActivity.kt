package com.example.alexandrechristie.ui.login

import android.R
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.example.alexandrechristie.AppSharedPreference
import com.example.alexandrechristie.MainActivity
import com.example.alexandrechristie.data.entity.User
import com.example.alexandrechristie.data.entity.UserLogin
import com.example.alexandrechristie.databinding.ActivityLoginBinding
import com.example.alexandrechristie.ui.home.admin.AdminActivity
import com.example.alexandrechristie.ui.home.order.OrderActivity
import com.example.alexandrechristie.ui.profile.ProfileFragment
import com.example.alexandrechristie.ui.register.RegisterActivity
import com.example.alexandrechristie.viewModel.UserLoginViewModel
import com.example.alexandrechristie.viewModel.UserViewModel
import com.google.android.material.snackbar.Snackbar


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private lateinit var userList: ArrayList<User>
    private var id:Int ?=null
    private var uname: String? = null
    private lateinit var phone: String
    private lateinit var pwd : String
    private lateinit var email: String
    private lateinit var address : String
    private lateinit var gender : String
    private var mProfileUri: Uri?=null


    private lateinit var phoneNumber: String
    private lateinit var password:String

    private val viewModel: UserViewModel by viewModels()
    private val viewUserModel: UserLoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkValidate()

        binding.login.setOnClickListener {
            phoneNumber = binding.phone.text.toString()
            password = binding.pwd.text.toString()

            if(phoneNumber.isNullOrEmpty()){
                binding.tilPhone.error = "Enter Phone Number"
                binding.tilPhone.isErrorEnabled=true
                return@setOnClickListener
            }
            if(password.isNullOrEmpty()){
                binding.tilPassword.error="Enter Password"
                binding.tilPassword.isErrorEnabled = true
                return@setOnClickListener
            }
            if(phoneNumber.length<11){
                binding.tilPhone.error = "Enter Correct Phone Number"
                binding.tilPhone.isErrorEnabled=true
                return@setOnClickListener
            }
            if(phoneNumber == "09794713191" && password == "admin123"){
                Toast.makeText(this@LoginActivity, "Welcome admin", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@LoginActivity, AdminActivity::class.java)
                startActivity(intent)
                clear()
//                finish()
            }else if(phoneNumber != "09794713191" && password != "admin123"){
                getUser()
            }
            else{
                Snackbar.make(binding.root,"Enter Correct Phone Number and Correct Password",
                    Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }

        binding.registerNow.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun getUser(){
        viewModel.getUser().observe(this) setOnClickListener@{
            userList=it as ArrayList<User>
            if (userList.isNotEmpty()){
                for(user in userList){
                    if ((user.phone == phoneNumber) && (user.password == password)){
                        Toast.makeText(this, "Successful Login", Toast.LENGTH_SHORT).show()
                        uname=user.userName
                        phone=user.phone
                        email=user.email
                        pwd=user.password
                        address=user.address
                        gender=user.gender
                        id=user.id
                        mProfileUri=Uri.parse(user.userImg)

                        upsertUserLogin()
                        startActivity(Intent(this,MainActivity::class.java))
                        finish()
                        break
                    }
                    if ((user.phone != phoneNumber) && (user.password != password)){
                        startActivity(Intent(this@LoginActivity,RegisterActivity::class.java))
                        Toast.makeText(this, "This Phone Number is not registered. Please Register", Toast.LENGTH_SHORT).show()
                    }
                    if ((user.phone == phoneNumber) && (user.password != password)){
                        binding.tilPassword.error="Enter Correct Password"
                        binding.tilPassword.isErrorEnabled = true
                        binding.pwd.setText("")
                        return@setOnClickListener
                    }
                    if ((user.phone != phoneNumber) && (user.password == password)) {
                        binding.tilPhone.error = "Enter Correct Phone Number"
                        binding.tilPhone.isErrorEnabled = true
                        binding.phone.setText("")
                        return@setOnClickListener
                    }
                }

            }else{
                Toast.makeText(this@LoginActivity, "This Phone Number is not registered.", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@LoginActivity,RegisterActivity::class.java))
            }

        }
    }

    private fun clear() {
        binding.phone.setText("")
        binding.pwd.setText("")
    }

    private fun checkValidate() {
        binding.phone.doAfterTextChanged { s->
            if(!s.isNullOrEmpty()){
                if(s.length >8){
                    binding.tilPhone.isErrorEnabled = false
                }
            }
        }
        binding.pwd.doAfterTextChanged { s->
            if(!s.isNullOrEmpty()){
                binding.tilPassword.isErrorEnabled = false
            }
        }
    }
    private fun upsertUserLogin() {
        val userId = id
        val name=uname.toString().trim()
        val mail=email.trim()
        val phoneNo=phone.trim()
        val pass=pwd.trim()
        val gen = gender.trim()
        val addresses=address.trim()

        val userLogin=UserLogin(
            id, userId,name, mail , phoneNo,pass,gen,addresses,mProfileUri.toString()
        )
        viewUserModel.insertUserLogin(userLogin)
    }
}