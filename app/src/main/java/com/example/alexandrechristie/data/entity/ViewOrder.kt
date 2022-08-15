package com.example.alexandrechristie.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class ViewOrder(
        @PrimaryKey(autoGenerate = true)
        val id: Int ?= null,
        val watchId: Int = 0,
        val watchCode: String,
        val price: Int,
        val qty: Int,
        val total : Int,
        val orderImg: String,
        val userName: String,
        val phone: String,
        val email: String,
        val orderDate: String
        )