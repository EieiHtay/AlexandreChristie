package com.example.alexandrechristie.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Watch(
    @PrimaryKey(autoGenerate = true)
    val id:Int ?= null,
    val code:String,
    val price:Int,
    val description:String,
    val img: String,
    val category: String
)