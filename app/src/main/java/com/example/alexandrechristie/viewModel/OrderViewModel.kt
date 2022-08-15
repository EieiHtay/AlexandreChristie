package com.example.alexandrechristie.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.alexandrechristie.data.entity.WatchOrder
import com.example.alexandrechristie.data.entity.WatchOrderResponse
import com.example.alexandrechristie.data.repository.OrderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrderViewModel(application: Application):AndroidViewModel(application) {
    private val repository : OrderRepository by lazy { OrderRepository(application) }

    fun getWatchOrder(query: String): LiveData<List<WatchOrderResponse>> {
        return repository.getWatchOrderList(query)
    }
    fun insertWatchOrderForm(order: WatchOrder){
        viewModelScope.launch {
            repository.insertWatchOrder(order)
        }
    }
    fun deleteOrder(watchId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteOrder(watchId)
        }
    }
    fun deleteAllOrder(){
        viewModelScope.launch (Dispatchers.IO){
            repository.deleteAllOrder()
        }
    }
}