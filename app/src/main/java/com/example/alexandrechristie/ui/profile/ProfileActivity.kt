package com.example.alexandrechristie.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.alexandrechristie.R
import com.example.alexandrechristie.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var name : String
    private lateinit var phone : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        name=intent.getStringExtra("name").toString()
//        phone = intent.getStringExtra("phone").toString()

//        val fragment = ProfileFragment()
//        fragment.name=name
//        fragment.phone=phone

        Toast.makeText(this, "${name}", Toast.LENGTH_SHORT).show()
    }
}