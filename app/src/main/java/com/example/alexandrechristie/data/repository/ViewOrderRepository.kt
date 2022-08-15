package com.example.alexandrechristie.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.alexandrechristie.AppConfig
import com.example.alexandrechristie.data.entity.ViewOrder
import com.example.alexandrechristie.data.entity.WatchOrder

class ViewOrderRepository(application: Application) {
    val db = (application as AppConfig).database

    fun getViewOrderListByEmail(email:String): LiveData<List<ViewOrder>> {
        val viewOrderList=db.viewOrderDao.getViewOrderByEmail(email)
        return viewOrderList
    }
    suspend fun insertViewOrder(viewOrder: ViewOrder){
        db.viewOrderDao.insertViewOrder(viewOrder)
    }

}