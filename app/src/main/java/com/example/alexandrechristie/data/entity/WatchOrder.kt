package com.example.alexandrechristie.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WatchOrder(
    @PrimaryKey(autoGenerate = true)
    val id : Int ?= null,
    val watchId : Int = 0,
    val watchCode : String,
    val price : Int,
    val qty : Int,
    val orderImg : String
)

data class WatchOrderResponse(
    val watchId: Int = 0,
    val watchCode: String,
    val price: Int,
    val qty: Int,
    val orderImg: String,
    val totalQtyPerFoodId : Int
)