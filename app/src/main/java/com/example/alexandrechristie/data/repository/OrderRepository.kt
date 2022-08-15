package com.example.alexandrechristie.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.alexandrechristie.AppConfig
import com.example.alexandrechristie.data.entity.WatchOrder
import com.example.alexandrechristie.data.entity.WatchOrderResponse

class OrderRepository (application: Application){
    val db = (application as AppConfig).database

    fun getWatchOrderList(query: String): LiveData<List<WatchOrderResponse>> {
        val watchOrderList= db.orderDao.searchWatchOrderListing()
        return watchOrderList
    }
    fun getWatchOrderListById(id:Int): LiveData<WatchOrder> {
        val order=db.orderDao.getWatchOrderById(id)
        return order
    }

    suspend fun insertWatchOrder(watchOrder: WatchOrder){
        db.orderDao.insertOrder(watchOrder)
    }

    suspend fun deleteOrder(watchId: Int) {
        db.orderDao.deleteOrder(watchId)
    }
    fun deleteAllOrder(){
        db.orderDao.deleteAll()
    }
}