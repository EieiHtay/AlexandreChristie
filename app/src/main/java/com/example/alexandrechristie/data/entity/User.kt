package com.example.alexandrechristie.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val id : Int ?= null,
    val userName : String,
    val email : String,
    val phone : String,
    val password : String,
    val confirmPassword : String,
    val gender : String,
    val address : String,
    val userImg : String
)