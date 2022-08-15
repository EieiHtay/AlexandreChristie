package com.example.alexandrechristie.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.alexandrechristie.data.dao.*
import com.example.alexandrechristie.data.entity.*

@Database(
    entities = [Watch::class,WatchOrder::class,User::class,UserLogin::class,ViewOrder::class], version = 14, exportSchema = false
)
abstract class WatchDatabase : RoomDatabase(){
    abstract val watchDao : WatchDao
    abstract val orderDao : OrderDao
    abstract val userDao : UserDao
    abstract val userLoginDao : UserLoginDao
    abstract val viewOrderDao : ViewOrderDao

    companion object{
        private var INSTANCE : WatchDatabase ?= null

        fun getDatabase(context: Context):WatchDatabase{
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    WatchDatabase::class.java,
                    "localClassDb",
                ).fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE!!
        }
    }
}