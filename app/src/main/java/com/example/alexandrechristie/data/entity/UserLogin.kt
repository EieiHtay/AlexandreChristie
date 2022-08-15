package com.example.alexandrechristie.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserLogin (
    @PrimaryKey(autoGenerate = true)
    val id : Int ?= null,
    val userId : Int ?= null,
    val userName : String,
    val email : String,
    val phone : String,
    val password : String,
    val gender : String,
    val address : String,
    val userImg : String
        )